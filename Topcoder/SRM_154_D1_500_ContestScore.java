package Topcoder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.StringTokenizer;

public class SRM_154_D1_500_ContestScore {

	static final double EPS = 1e-9;

	static class Entry {
		int idx;
		double score;

		Entry(int i, double s) {
			idx = i;
			score = s;
		}

		@Override
		public String toString() {
			return idx + " " + score;
		}
	}

	static class Group implements Comparable<Group> {
		String name;
		ArrayList<Integer> ranks;
		ArrayList<Double> scores;

		Group(String n, ArrayList<Double> scores) {
			name = n;
			ranks = new ArrayList<>();
			this.scores = scores;
		}

		void addRank(int rank) {
			ranks.add(rank);
		}

		int getLastRank() {
			return ranks.get(ranks.size() - 1);
		}

		int getRanks() {
			int sum = 0;
			for (int x : ranks)
				sum += x;
			return sum;
		}

		double getScores() {
			double sum = 0;
			for (double x : scores)
				sum += x;
			return sum;
		}

		@Override
		public String toString() {
			return String.format("%s %d %.1f", name, getRanks(), getScores());
		}

		@Override
		public int compareTo(Group g) {
			int r1 = getRanks(), r2 = g.getRanks();
			if (r1 != r2)
				return r1 - r2;
			double s1 = getScores(), s2 = g.getScores();
			if (Math.abs(s1 - s2) > EPS)
				return Double.compare(s2, s1);
			return name.compareTo(g.name);
		}
	}

	public static String[] sortResults(String[] data) {
		int m = 0;
		ArrayList<ArrayList<Double>> a = new ArrayList<>();
		int n = data.length;
		Group[] g = new Group[n];
		for (int i = 0; i < data.length; i++) {
			StringTokenizer st = new StringTokenizer(data[i]);
			String name = st.nextToken();
			ArrayList<Double> scores = new ArrayList<>();
			while (st.hasMoreTokens())
				scores.add(Double.parseDouble(st.nextToken()));
			a.add(scores);
			m = scores.size();
			g[i] = new Group(name, scores);
		}
		for (int i = 0; i < m; i++) {
			ArrayList<Entry> e = new ArrayList<>();
			for (int j = 0; j < n; j++)
				e.add(new Entry(j, a.get(j).get(i)));
			Collections.sort(e, (x, y) -> Double.compare(y.score, x.score));
			// System.err.println(e);
			for (int j = 0; j < e.size(); j++)
				if (j == 0)
					g[e.get(j).idx].addRank(j + 1);
				else {
					if (e.get(j).score == e.get(j - 1).score)
						g[e.get(j).idx].addRank(g[e.get(j - 1).idx].getLastRank());
					else
						g[e.get(j).idx].addRank(j + 1);
				}
		}
		// System.err.println(Arrays.toString(g));
		Arrays.sort(g);
		String[] result = new String[g.length];
		for (int i = 0; i < g.length; i++)
			result[i] = g[i].toString();
		return result;
	}
}
