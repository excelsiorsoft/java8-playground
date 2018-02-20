package collectors;

import static java.lang.ClassLoader.getSystemResource;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.IntSummaryStatistics;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.function.ToIntFunction;
import java.util.stream.Collectors;

import org.junit.Test;

public class CollectorsTests {

	@Test
	public void testStreamStats() throws Exception {

		// http://introcs.cs.princeton.edu/java/data/words.shakespeare.txt
		// http://introcs.cs.princeton.edu/java/data/ospd.txt

		Set<String> shakespeareWords = Files.lines(Paths.get(getSystemResource("words.shakespeare.txt").toURI()))
				.map(word -> word.toLowerCase()).collect(Collectors.toSet());

		Set<String> scrabbleWords = Files.lines(Paths.get(getSystemResource("ospd.txt").toURI()))
				.map(word -> word.toLowerCase()).collect(Collectors.toSet());

		System.out.println("# words of Shakespeare : " + shakespeareWords.size());
		System.out.println("# words of Scrabble : " + scrabbleWords.size());

		final int[] scrabbleENScore = {
				// a, b, c, d, e, f, g, h, i, j, k, l, m, n, o, p, q, r, s, t, u, v, w, x, y, z
				1, 3, 3, 2, 1, 4, 2, 4, 1, 8, 5, 1, 3, 1, 1, 3, 10, 1, 1, 1, 1, 4, 4, 8, 4, 10 };

		Function<String, Integer> score = word -> word.chars().map(letter -> scrabbleENScore[letter - 'a']).sum();

		Map<Integer, List<String>> histogramWordsByScore =
				shakespeareWords.stream().collect(Collectors.groupingBy(score));
		
		System.out.println("# of buckets in histogramWordsByScore=" + histogramWordsByScore.size());
	}
	
}
