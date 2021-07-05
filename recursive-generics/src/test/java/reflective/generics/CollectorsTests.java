package reflective.generics;

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

	@SuppressWarnings("unlikely-arg-type")
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
				shakespeareWords.stream()
				.filter(word -> scrabbleWords.contains(word)) //remove non-words
				.collect(Collectors.groupingBy(score));
		
		System.out.println("# of buckets in histogramWordsByScore=" + histogramWordsByScore.size());
		
		histogramWordsByScore.entrySet() //Set<Map.Entry<Integer, List<String>>>
			.stream()
			.sorted(Comparator.comparing(entry -> -entry.getKey()))
			.limit(3)
			.forEach(entry -> System.out.println(entry.getKey() + ":" + entry.getValue()));;
			
			int [] scrabbleENDistribution = {
	                // a, b, c, d,  e, f, g, h, i, j, k, l, m, n, o, p, q, r, s, t, u, v, w, x, y, z
	                   9, 2, 2, 1, 12, 2, 3, 2, 9, 1, 1, 4, 2, 6, 8, 2, 1, 6, 4, 6, 4, 2, 2, 1, 2, 1} ;
			
			Function<String, Map<Integer, Long>> histogramOfLettersInWord = 
					word -> word.chars().boxed().collect(Collectors.groupingBy(letter->letter, Collectors.counting()));
					
					Function<String, Long> nBlanks = 
		                    word -> histogramOfLettersInWord.apply(word) // Map<Integer, Long> Map<letter, # of letters>
		                                .entrySet()
		                                .stream() // Map.Entry<Integer, Long>
		                                .mapToLong(
		                                        entry -> 
		                                            Long.max(
		                                                entry.getValue() -
		                                                (long)scrabbleENDistribution[entry.getKey() - 'a'], 
		                                                0L
		                                            )
		                                )
		                                .sum();
		            System.out.println("# of blanks for whizzing : " + nBlanks.apply("whizzing"));	
		            
		            Function<String, Integer> score2 = 
		                    word -> histogramOfLettersInWord.apply(word)
		                                .entrySet()
		                                .stream() // Map.Entry<Integer, Long>
		                                .mapToInt(
		                                      entry ->
		                                      		  scrabbleENScore[entry.getKey() - 'a']*
		                                              Integer.min(
		                                                      entry.getValue().intValue(), 
		                                                      scrabbleENDistribution[entry.getKey() - 'a']
		                                              )
		                                )
		                                .sum();
		            System.out.println("# score for whizzing  : " + score.apply("whizzing"));
		            System.out.println("# score2 for whizzing : " + score2.apply("whizzing"));
		            
		          //Map<Integer, List<String>> histoWordsByScore2 = 
                    shakespeareWords.stream()
                        .filter(scrabbleWords::contains)
                        .filter(word -> nBlanks.apply(word) <= 2)
                        .collect(
                                Collectors.groupingBy(
                                        score2
                                )
                        )
                        .entrySet() // Set<Map.Entry<Integer, List<String>>>
                        .stream()
                        .sorted(
                                Comparator.comparing(entry -> -entry.getKey())
                        )
                        .limit(3)
                        .forEach(entry -> System.out.println(entry.getKey() + " - " + entry.getValue()));
                                
	}
	
}
