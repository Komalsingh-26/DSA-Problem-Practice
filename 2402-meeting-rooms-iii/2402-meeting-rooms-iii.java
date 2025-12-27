class Solution {
    public int mostBooked(int n, int[][] meetings) {
        Arrays.sort(meetings, (a, b) -> Integer.compare(a[0], b[0]));
        PriorityQueue<long[]> busyRooms = new PriorityQueue<>((a, b) -> {
            if (a[0] == b[0]) return Long.compare(a[1], b[1]);
            return Long.compare(a[0], b[0]);
        });
        TreeSet<Integer> freeRooms = new TreeSet<>();
        for (int i = 0; i < n; i++) freeRooms.add(i);

        int[] count = new int[n]; 

        for (int[] meeting : meetings) {
            int start = meeting[0];
            int end = meeting[1];
            int duration = end - start;
            while (!busyRooms.isEmpty() && busyRooms.peek()[0] <= start) {
                int room = (int) busyRooms.poll()[1];
                freeRooms.add(room);
            }

            if (!freeRooms.isEmpty()) {
                int room = freeRooms.pollFirst();
                busyRooms.offer(new long[]{start + duration, room});
                count[room]++;
            } else {
                long[] next = busyRooms.poll();
                long newEnd = next[0] + duration;
                int room = (int) next[1];
                busyRooms.offer(new long[]{newEnd, room});
                count[room]++;
            }
        }

        int maxMeetings = 0;
        int resultRoom = 0;
        for (int i = 0; i < n; i++) {
            if (count[i] > maxMeetings) {
                maxMeetings = count[i];
                resultRoom = i;
            }
        }

        return resultRoom;
    }
}
