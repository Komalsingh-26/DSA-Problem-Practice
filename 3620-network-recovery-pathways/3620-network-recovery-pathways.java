import java.util.*;

class Solution {
    static class Edge {
        int to;
        int cost;

        Edge(int to, int cost) {
            this.to = to;
            this.cost = cost;
        }
    }

    public int findMaxPathScore(int[][] edges, boolean[] online, long k) {
        int n = online.length;

        List<Edge>[] graph = new ArrayList[n];
        for (int i = 0; i < n; i++) graph[i] = new ArrayList<>();

        int[] indegree = new int[n];
        int[] values = new int[edges.length];

        for (int i = 0; i < edges.length; i++) {
            int u = edges[i][0];
            int v = edges[i][1];
            int c = edges[i][2];
            graph[u].add(new Edge(v, c));
            indegree[v]++;
            values[i] = c;
        }

      
        int[] topo = new int[n];
        Queue<Integer> q = new ArrayDeque<>();
        for (int i = 0; i < n; i++) {
            if (indegree[i] == 0) q.offer(i);
        }

        int idx = 0;
        while (!q.isEmpty()) {
            int u = q.poll();
            topo[idx++] = u;
            for (Edge e : graph[u]) {
                if (--indegree[e.to] == 0) q.offer(e.to);
            }
        }

        Arrays.sort(values);
        int[] uniq = new int[values.length];
        int m = 0;
        for (int v : values) {
            if (m == 0 || uniq[m - 1] != v) {
                uniq[m++] = v;
            }
        }

        int lo = 0, hi = m - 1;
        int ans = -1;

        while (lo <= hi) {
            int mid = (lo + hi) >>> 1;
            int threshold = uniq[mid];

            if (canReach(graph, topo, online, k, threshold, n)) {
                ans = threshold;
                lo = mid + 1;
            } else {
                hi = mid - 1;
            }
        }

        return ans;
    }

    private boolean canReach(List<Edge>[] graph, int[] topo, boolean[] online,
                             long k, int threshold, int n) {

        long INF = Long.MAX_VALUE / 4;
        long[] dist = new long[n];
        Arrays.fill(dist, INF);
        dist[0] = 0;

        for (int u : topo) {
            if (dist[u] == INF) continue;

            if (u != 0 && u != n - 1 && !online[u]) continue;

            for (Edge e : graph[u]) {
                if (e.cost < threshold) continue;

                int v = e.to;
                if (v != n - 1 && !online[v]) continue;

                long nd = dist[u] + e.cost;
                if (nd < dist[v]) {
                    dist[v] = nd;
                }
            }
        }

        return dist[n - 1] <= k;
    }
}