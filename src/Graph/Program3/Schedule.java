package Graph.Program3;

import java.security.InvalidParameterException;
import java.util.*;

public class Schedule {
    class Job {
        int duration;             // like an edge weight
        List<Job> prerequisites;  // same as incoming edges
        List<Job> ableToDoNext;   // same as outgoing edges
        int jobId;                // vertex id
        int indegree;

        public Job(int duration) {
            this.duration = duration;
            this.prerequisites = new ArrayList<>();
            this.ableToDoNext = new ArrayList<>();
            this.jobId = -1;
            this.indegree = 0;
        }

        public void requires(Job j) {
            this.prerequisites.add(j);
            j.ableToDoNext.add(this);
            this.indegree++;
        }

        public int start() {
            //TODO
            // It will return the earliest possible start time for the job.
            // (The very earliest first start time, for jobs with no pre-requisites, is 0.)
            // If there IS a cycle, and thus the given job can never be started (because it is on the cycle,
            // or something on the cycle must be completed before this job starts), return -1.
            // However, if the job can be started,
            // even if other jobs within the overall schedule cannot be,
            // return a valid time.

            getTopologicalOrder();
            if (topologicalOrder.size() <= jobId) return -1;  // means cycle exists before hint this job

            //FIXME : this is not working,  maybe try find longest path from this, update predTracker, then back track from this to nearest vertex who has no prerequisite.
            return timeOfAllPrerequisitesNeeded();
        }

        private int timeOfAllPrerequisitesNeeded() { // this can't find cycle !!!!!
            if (this.indegree == 0) return this.duration;

            int sum = 0;
            for (Job prereqJob : this.prerequisites) {
                sum += prereqJob.timeOfAllPrerequisitesNeeded();
            }
            return sum;
        }


    }

    List<Job> jobList;
    List<Integer> topologicalOrder;


    public Schedule() {
        this.jobList = new ArrayList<>();
        this.topologicalOrder = new ArrayList<>();
    }

    /**
     * insert a new job to the schedule, return the job id of the new job that just inserted
     */
    public Job insert(int time) {
        if (time < 0) throw new InvalidParameterException("Time has to be positive");
        Job newJob = new Job(time);
        jobList.add(newJob);
        newJob.jobId = jobList.size() - 1;
        return newJob;
    }

    /**
     * return a Job of the given index
     */
    public Job get(int index) {
        if (index < 0 || index >= jobList.size()) return null;
        return jobList.get(index);
    }

    public int finish() {
        //TODO
        // public int finish() should return the earliest possible completion time for the entire Schedule,
        // or -1 if it is not possible to complete it.
        // (It is not possible to complete if there is a prerequisite cycle within the underlying graph).
        getTopologicalOrder();
        if (topologicalOrder.size() != jobList.size()) return -1; // cycle exist

        int counter = 0;
        for (int i : topologicalOrder) {
            counter += jobList.get(i).duration;
        }

        return counter;
    }

    /**
     * FIXME: The core problem is, can we do multiple jobs at same time. Topological order only work for if I assume that we only can do one job each time.
     * If we can do more than one job at a time.
     */
    private void getTopologicalOrder() {
        int n = jobList.size();
        topologicalOrder.clear(); // reset list
        int[] indegreeTrack = new int[n];

        Deque<Integer> que = new ArrayDeque<>();
        for (int i = 0; i < n; i++) {
            indegreeTrack[i] = jobList.get(i).indegree; // add to tracker
            if (jobList.get(i).indegree == 0) { // offer all 0-indegree vertices
                que.offer(i);
                topologicalOrder.add(i);
            }
        }

        while (!que.isEmpty()) {
            int cur = que.poll();
            for (Job j : get(cur).ableToDoNext) { // all next outgoing vertices from cur
                indegreeTrack[j.jobId]--;  // decrease indegree tracker
                if (indegreeTrack[j.jobId] == 0) {
                    topologicalOrder.add(j.jobId);
                    que.offer(j.jobId);    // offer to que and start again for here
                }
            }
        }
    }

    private List<Integer> findAllZeroIndegreeVertices() {
        List<Integer> res = new ArrayList<>();
        for (Job j : jobList) {
            if (j.indegree == 0) res.add(j.jobId);
        }
        return res;
    }

    private class Result {
        int[] distMap;
        int[] predTrack;

        public Result(int[] d, int[] p) {
            this.distMap = d;
            this.predTrack = p;
        }
    }

    private Result findShortestPath(int start, int end) {


        int n = jobList.size();


        int[] dist = new int[n];
        Arrays.fill(dist, Integer.MAX_VALUE);
        dist[start] = 0;
        boolean[] isVisited = new boolean[n];
        Arrays.fill(isVisited, false);

        int[] predTrack = new int[jobList.size()];
        Arrays.fill(predTrack, -1);

        for (int i = 0; i < n; i++) { // loop all vertices
            int cur = -1;
            int min = Integer.MAX_VALUE; // init min = max
            for (int j = 0; j < n; j++) {
                if (dist[j] < min && !isVisited[j]) {
                    min = dist[j];
                    cur = j;    // find a cur that cur is nearest from i
                }
            }

            if (cur == -1) break; // means cur is not reachable

            isVisited[cur] = true;

            if (cur == end) break;

            for (int j = 0; j < n; j++) {
                if (!isVisited[j] && jobList.get(j).ableToDoNext.contains(jobList.get(cur))) { // cur needs j and j is not visit yet
                    if (dist[cur] + jobList.get(j).duration < dist[j]) {
                        dist[j] = dist[cur] + jobList.get(j).duration;
                        predTrack[j] = cur;
                    }
                }
            }
        }
        return new Result(dist, predTrack);
    }

    private Result findLongestPath(int start) {
        int[] maxDist = new int[jobList.size()];
        Arrays.fill(maxDist, -1);
        maxDist[start] = 0;
        int[] pred = new int[jobList.size()];
        Arrays.fill(pred, -1);

        Queue<int[]> pq = new PriorityQueue<>(Comparator.comparing(i -> i[1]));
        pq.offer(new int[]{start, 0});

        while (!pq.isEmpty()) {
            int[] cur = pq.poll();
            int curV = cur[0];
            int curD = cur[1];

            if (curD != maxDist[cur[0]]) continue; // 过期条目,替代 isVisited flag

            for (Job nextJ : get(curV).ableToDoNext) {
                if (maxDist[nextJ.jobId] > maxDist[curV] + curD) {
                    maxDist[nextJ.jobId] = maxDist[curV] + curD;
                    pred[nextJ.jobId] = curV;
                    pq.offer(new int[]{nextJ.jobId, nextJ.duration});
                }
            }
        }
        return new Result(maxDist, pred);
    }


}



