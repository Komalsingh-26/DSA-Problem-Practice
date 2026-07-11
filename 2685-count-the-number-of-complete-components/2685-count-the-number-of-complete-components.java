class Solution {

    List<Integer>[] graph;
    boolean[] visited;
    int nodes;
    int degreeSum;

    public int countCompleteComponents(int n, int[][] edges) {

        graph = new ArrayList[n];
        for (int i = 0; i < n; i++) {
            graph[i] = new ArrayList<>();
        }

        for (int[] e : edges) {
            graph[e[0]].add(e[1]);
            graph[e[1]].add(e[0]);
        }

        visited = new boolean[n];

        int ans = 0;

        for (int i = 0; i < n; i++) {
            if (!visited[i]) {
                nodes = 0;
                degreeSum = 0;

                dfs(i);

                int edgeCount = degreeSum / 2;
                int requiredEdges = nodes * (nodes - 1) / 2;

                if (edgeCount == requiredEdges)
                    ans++;
            }
        }

        return ans;
    }

    private void dfs(int node) {
        visited[node] = true;
        nodes++;
        degreeSum += graph[node].size();

        for (int nei : graph[node]) {
            if (!visited[nei]) {
                dfs(nei);
            }
        }
    }
}