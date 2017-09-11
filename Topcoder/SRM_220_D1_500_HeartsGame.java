package Topcoder;

import java.util.Arrays;
import java.util.TreeSet;

public class SRM_220_D1_500_HeartsGame {

	private static int get(char c) {
		if (Character.isDigit(c))
			return c - '0';
		if (c == 'A')
			return 14;
		if (c == 'K')
			return 13;
		if (c == 'Q')
			return 12;
		if (c == 'J')
			return 11;
		return 10;
	}

	public static String[] score(String[] tricks) {
		TreeSet<Character>[] noHave = new TreeSet[4];
		for (int i = 0; i < 4; i++)
			noHave[i] = new TreeSet<>();
		boolean[] cheater = new boolean[4];
		boolean[] allHearts = new boolean[4];
		boolean[] playedScoringOnFirst = new boolean[4];
		int[] points = new int[4];
		boolean heartsBroken = false;
		int lead = 0;
		for (int j = 0; j < tricks.length; j++) {
			String[] s = tricks[j].split(" ");
			char mainSuit = s[0].charAt(0);
			int pnts = 0, winner = 0, highest = -1;
			for (int i = 0; i < 4; i++) {
				char suit = s[i].charAt(0), rank = s[i].charAt(1);
				int h = get(rank), player = (lead + i) % 4;
				if (suit == mainSuit && h > highest) {
					highest = h;
					winner = i;
				}
				if (suit != mainSuit)
					noHave[player].add(mainSuit);

				if (suit == 'H' && !heartsBroken && i == 0)
					allHearts[player] = true;

				if (suit == 'H')
					heartsBroken = true;

				boolean playedHeartOrQueen = false;
				if (suit == 'H') {
					pnts++;
					playedHeartOrQueen = true;
				}
				if (suit == 'S' && rank == 'Q') {
					pnts += 13;
					playedHeartOrQueen = true;
				}

				if (playedHeartOrQueen && j == 0)
					playedScoringOnFirst[player] = true;

				// Played an illegal card before although he should not have
				if (noHave[player].contains(suit))
					cheater[player] = true;
				// Played Scoring then non-scoring
				if (j > 0 && !playedHeartOrQueen && playedScoringOnFirst[player])
					cheater[player] = true;
				// Played hearts before but he should not have
				if (allHearts[player] && suit != 'H')
					cheater[player] = true;
			}
			points[(winner + lead) % 4] += pnts;
			lead = (winner + lead) % 4;
		}
		for (int i = 0; i < 4; i++)
			if (points[i] == 26) {
				Arrays.fill(points, 26);
				points[i] = 0;
				break;
			}
		String[] res = new String[4];
		for (int i = 0; i < 4; i++)
			if (cheater[i])
				res[i] = "CHEATER!";
			else
				res[i] = Integer.toString(points[i]);
		return res;
	}

	public static void main(String[] args) {
		System.err.println(Arrays.toString(score(new String[] { "C2 CA DA C9", "S9 S8 S7 S5", "ST S6 S3 S4",
				"C6 HK C3 CK", "DQ HQ D9 D5", "D3 H7 D4 D2", "D6 SK DJ H8", "H5 H2 H6 H3", "H9 H4 HT CQ", "D8 CJ DT SA",
				"HJ C7 SQ CT", "HA C5 D7 C8", "S2 SJ DK C4" })));

		System.err.println(Arrays.toString(score(new String[] { "C2 D2 S2 H2", "C3 D3 S3 H3", "C4 D4 S4 H4",
				"C5 D5 S5 H5", "C6 D6 S6 H6", "C7 D7 S7 H7", "C8 D8 S8 H8", "C9 D9 S9 H9", "CT DT ST HT", "CJ DJ SJ HJ",
				"CQ DQ SQ HQ", "CK DK SK HK", "CA DA SA HA" })));

		System.err.println(Arrays.toString(score(new String[] { "C2 C3 CK SA", "DA DK DT D8", "SJ ST S3 SK",
				"S5 S6 S2 CQ", "C6 DQ CT HK", "H3 H6 H2 H4", "S4 S9 S8 HQ", "C4 HA CJ HT", "C7 HJ C5 H9", "C8 D9 C9 CA",
				"H5 H7 D7 H8", "D2 D6 D4 D5", "D3 S7 SQ DJ" })));

		System.err.println(Arrays.toString(score(new String[] { "C2 C9 CA CT", "D4 D2 D3 DJ", "S9 S2 S7 SA",
				"C4 C7 D5 CJ", "D6 C6 DK DT", "CK H7 C8 CQ", "HA HJ H8 H6", "ST HK S3 S4", "SK H5 SJ S6", "S8 H9 D8 HQ",
				"SQ HT DA C3", "C5 D9 H2 H3", "S5 D7 DQ H4" })));

		System.err.println(Arrays.toString(score(new String[] { "C2 CQ HK D7", "H9 H7 HJ H4", "S2 S8 S5 D2",
				"C9 DA CT CK", "DT C8 D9 D4", "DK S3 H6 C6", "CJ D6 C5 S9", "D8 SA H8 DJ", "S7 S6 ST CA", "HA H5 H3 D5",
				"DQ C7 SK D3", "HQ H2 SJ C3", "SQ C4 HT S4" })));

		System.err.println(Arrays.toString(score(new String[] { "C2 CA HA CQ", "C3 HK C9 C5", "ST SJ SA SQ",
				"C4 HQ C6 CJ", "S9 DA HJ S7", "H2 DT HT SK", "H3 DQ H4 CK", "S4 CT H9 S6", "S5 S3 C8 H8", "D6 D4 D5 D3",
				"D8 DK D7 H7", "S2 C7 H6 DJ", "S8 D2 H5 D9" })));

		System.err.println(Arrays.toString(score(new String[] { "C2 SQ C9 C8", "C6 CA CQ HA", "S4 SJ HK SK",
				"ST SA S9 HQ", "DA DK D7 DQ", "D2 D4 HJ DJ", "H4 DT H2 H3", "D9 D5 D3 HT", "S5 CK S8 H9", "CJ H8 D8 CT",
				"C5 H7 D6 C4", "S2 H6 S7 C7", "S6 C3 S3 H5" })));

		System.err.println(Arrays.toString(score(new String[] { "C2 CJ C6 DK", "H7 S2 DQ H6", "H2 S6 DJ CK",
				"H3 S8 D2 S4", "HK ST H5 CA", "H8 SA D3 C7", "DA D4 DT C8", "HQ S9 D8 C4", "HJ S7 D9 CT", "HT SK SQ S5",
				"H4 SJ D5 C9", "HA S3 D6 C5", "H9 CQ D7 C3" })));
	}
}
