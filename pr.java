import java.util.Scanner;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

// http://snap.stanford.edu/data/cit-HepTh.html

public class pr 
{
	private static int max(int i, int j) {
		if (i >= j) {
			return i;
		}
		return j;
	}

	public static void main(String[] args) throws IOException
	{
		int N = 25059;
		double c = 0.05;

		System.out.println("Making List adj of links into an id");
		Map<Integer, List<Integer>> adj = new HashMap<Integer,List<Integer>>();

		Scanner fileScanner = new Scanner(new File("./cit-HepTh.txt"));
		while ( fileScanner.hasNextLine() ) {
			String[] tf;
			tf = fileScanner.nextLine().split("\t");
			int from = Integer.parseInt(tf[0]);
			int to = Integer.parseInt(tf[1]);
			List<Integer> t = adj.get(from);
			if (t == null) {
				t = new ArrayList<Integer>();
				adj.put(from,t);
			}
			t.add(to);

			List<Integer> l = adj.get(to);
			if (l == null) {
				l = new ArrayList<Integer>();
				adj.put(to,l);
			}
		}

		System.out.println("Random Walk");
		int num_iterations = 100000000;
		int cur = 1002;
		Random randomGenerator = new Random();

		List<Integer> places = new ArrayList<Integer>();
		places.addAll(adj.keySet());

		Map<Integer, Integer> counts = new HashMap<Integer, Integer>();

		for (int j = 0; j < N; ++j) {
			counts.put(j, 0);
		}

		for(int j = 0; j < num_iterations; j++) {
			List<Integer> possible = adj.get(cur);
			int possible_length = possible.size();
			if (Math.random() < c || possible_length == 0) {
				cur = places.get(randomGenerator.nextInt(N));
			}
			else {
				cur = possible.get(randomGenerator.nextInt(possible_length));
			}
			Integer x = counts.get(cur);
			if (x == null) {
				x = 1;
			}
			counts.put(cur, x+1);
		}

		int best_count = 0;
		int best_count_id = 0;

		List<Integer> count_keys = new ArrayList<Integer>();
		count_keys.addAll(counts.keySet());

		File file = new File("./ranks.csv");
		if (!file.exists()) {
			file.createNewFile();
		}		

		FileWriter fw = new FileWriter(file.getAbsoluteFile());
		BufferedWriter bw = new BufferedWriter(fw);

		for (Integer key : count_keys) {
			int value = counts.get(key);
			if (value > best_count) {
				best_count = value;
				best_count_id = key;
			}
			bw.write(key+","+value*1.0/num_iterations+"\n");
		}
		System.out.println(best_count_id);
		bw.close();
	}
}