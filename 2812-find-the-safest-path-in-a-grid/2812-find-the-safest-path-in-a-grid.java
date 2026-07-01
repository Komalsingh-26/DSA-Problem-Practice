class Solution {

    private final int[][] directions = {
        {1, 0}, {-1, 0}, {0, 1}, {0, -1}
    };

    public int maximumSafenessFactor(List<List<Integer>> grid) {
        int n = grid.size();

        int[][] dist = new int[n][n];
        for (int[] row : dist) {
            Arrays.fill(row, -1);
        }

        Queue<int[]> queue = new LinkedList<>();

        // Multi-source BFS from all thieves
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (grid.get(i).get(j) == 1) {
                    dist[i][j] = 0;
                    queue.offer(new int[]{i, j});
                }
            }
        }

        while (!queue.isEmpty()) {
            int[] cur = queue.poll();

            for (int[] d : directions) {
                int nr = cur[0] + d[0];
                int nc = cur[1] + d[1];

                if (nr >= 0 && nr < n && nc >= 0 && nc < n && dist[nr][nc] == -1) {
                    dist[nr][nc] = dist[cur[0]][cur[1]] + 1;
                    queue.offer(new int[]{nr, nc});
                }
            }
        }

        int low = 0;
        int high = 2 * n;

        while (low <= high) {
            int mid = low + (high - low) / 2;

            if (canReach(dist, mid)) {
                low = mid + 1;
            } else {
                high = mid - 1;
            }
        }

        return high;
    }

    private boolean canReach(int[][] dist, int limit) {
        int n = dist.length;

        if (dist[0][0] < limit || dist[n - 1][n - 1] < limit) {
            return false;
        }

        boolean[][] visited = new boolean[n][n];
        Queue<int[]> queue = new LinkedList<>();

        queue.offer(new int[]{0, 0});
        visited[0][0] = true;

        while (!queue.isEmpty()) {
            int[] cur = queue.poll();

            if (cur[0] == n - 1 && cur[1] == n - 1) {
                return true;
            }

            for (int[] d : directions) {
                int nr = cur[0] + d[0];
                int nc = cur[1] + d[1];

                if (nr >= 0 && nr < n && nc >= 0 && nc < n
                        && !visited[nr][nc]
                        && dist[nr][nc] >= limit) {

                    visited[nr][nc] = true;
                    queue.offer(new int[]{nr, nc});
                }
            }
        }

        return false;
    }
}