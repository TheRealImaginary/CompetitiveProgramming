package Topcoder;

public class SRM_156_D1_500_SmartElevator {

	static final int oo = 1 << 28;
	static int N;
	static int[] a, s, d, got;
	static int ans;

	static void solve(int floor, int num, int time) {
		if (num == N) {
			ans = Math.min(ans, time);
			return;
		}
		for (int i = 0; i < N; i++)
			if (got[i] == 0) {
				got[i] = 1;
				solve(s[i], num, Math.max(a[i], time + Math.abs(floor - s[i])));
				got[i] = 0;
			}
		for (int i = 0; i < N; i++)
			if (got[i] == 1) {
				got[i] = 2;
				solve(d[i], num + 1, time + Math.abs(floor - d[i]));
				got[i] = 1;
			}
	}

	public static int timeWaiting(int[] arrivalTime, int[] startingFloor, int[] destinationFloor) {
		N = arrivalTime.length;
		a = arrivalTime;
		s = startingFloor;
		d = destinationFloor;
		ans = oo;
		got = new int[N];
		solve(1, 0, 0);
		return ans;
	}

	public static void main(String[] args) {
		System.err.println(timeWaiting(new int[] { 5 }, new int[] { 30 }, new int[] { 50 }));
		System.err.println(timeWaiting(new int[] { 100 }, new int[] { 30 }, new int[] { 50 }));
		System.err.println(timeWaiting(new int[] { 10, 120 }, new int[] { 1, 100 }, new int[] { 210, 200 }));
	}
}
