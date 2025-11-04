package Graph.Program3;

import java.util.*;

public class Schedule {
    class Job {
        int duration;             // vertex weight
        List<Job> prerequisites;  // incoming edges
        List<Job> ableToDoNext;   // outgoing edges
        int jobId;                // vertex id
        int earliestFinish;
        Job pred;

        public Job(int duration) {
            this.duration = duration;
            this.prerequisites = new ArrayList<>();
            this.ableToDoNext = new ArrayList<>();
            this.jobId = -1;
            this.pred = null;
            this.earliestFinish = duration;
        }

        public void requires(Job j) {
            this.prerequisites.add(j);
            j.ableToDoNext.add(this);
            structureChanged = true;
        }

        public int start() {
            finish();
            if (!topologicalOrder.contains(this.jobId)) return -1; // linkedHasSet contain  O(1)
            return this.pred == null ? 0 : this.pred.earliestFinish;  // no pred means can start at time=0
        }
    }

    List<Job> jobList;
    Set<Integer> topologicalOrder;
    boolean structureChanged = true;
    int maxTimeNeeds = 0;  // there is only insert method, means maxTime never decrease

    public Schedule() {
        this.jobList = new ArrayList<>();
        this.topologicalOrder = new LinkedHashSet<>();
    }

    public Job insert(int time) {
        Job newJob = new Job(time);
        jobList.add(newJob);
        newJob.jobId = jobList.size() - 1;
        maxTimeNeeds = Math.max(maxTimeNeeds, time);  // try to update maxTime
        return newJob;
    }

    public Job get(int index) {
        return jobList.get(index);
    }

    public int finish() {
        if (structureChanged) {  // if no new edges, skip this
            init();
            getTopologicalOrder();
            relaxAll();
            structureChanged = false;
        }

        return topologicalOrder.size() == jobList.size() ? maxTimeNeeds : -1; // no cycle, return maxTime,  w/ cycle, return -1
    }

    private void relaxAll() {
        for (Integer j : topologicalOrder) {
            Job cur = jobList.get(j);
            for (Job nextJ : cur.ableToDoNext) {
                relax(cur, nextJ);
                maxTimeNeeds = Math.max(maxTimeNeeds, nextJ.earliestFinish); // update maxTimeNeeds right the way
            }
        }
    }

    private void init() {
        for (Job j : jobList) {
            j.earliestFinish = j.duration;
            j.pred = null;
        }
    }

    private void getTopologicalOrder() {
        // reset and init setting
        topologicalOrder.clear();
        int size = jobList.size();
        int[] indegreeMap = new int[size];

        // init queue
        Deque<Integer> que = new ArrayDeque<>();
        for (int i = 0; i < size; i++) {
            Job cur = jobList.get(i);
            indegreeMap[i] = cur.prerequisites.size();
            if (cur.prerequisites.isEmpty()) {
                que.offer(i);
            }
        }

        // iteration
        while (!que.isEmpty()) {
            int vId = que.poll();
            Job v = jobList.get(vId);
            topologicalOrder.add(vId);
            for (Job nextV : v.ableToDoNext) {
                indegreeMap[nextV.jobId]--;
                if (indegreeMap[nextV.jobId] == 0) {
                    que.offer(nextV.jobId);
                }
            }
        }
    }

    private void relax(Job v, Job nextV) {
        if (nextV.earliestFinish < nextV.duration + v.earliestFinish) {
            nextV.earliestFinish = nextV.duration + v.earliestFinish;
            nextV.pred = v;
        }
    }
}