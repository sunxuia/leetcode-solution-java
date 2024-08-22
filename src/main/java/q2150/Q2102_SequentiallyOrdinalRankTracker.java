package q2150;

import java.util.Comparator;
import java.util.PriorityQueue;
import org.junit.Assert;
import org.junit.Test;

/**
 * <h3>[Hard] 2102. Sequentially Ordinal Rank Tracker</h3>
 * <a href="https://leetcode.com/problems/sequentially-ordinal-rank-tracker/">
 * https://leetcode.com/problems/sequentially-ordinal-rank-tracker/
 * </a><br/>
 *
 * <p>A scenic location is represented by its <code>name</code> and attractiveness <code>score</code>, where
 * <code>name</code> is a <strong>unique</strong> string among all locations and <code>score</code> is an integer.
 * Locations can be ranked from the best to the worst. The <strong>higher</strong> the score, the better the location.
 * If the scores of two locations are equal, then the location with the <strong>lexicographically smaller</strong> name
 * is better.</p>
 *
 * <p>You are building a system that tracks the ranking of locations with the system initially starting with no
 * locations. It supports:</p>
 *
 * <ul>
 * 	<li><strong>Adding</strong> scenic locations, <strong>one at a time</strong>.</li>
 * 	<li><strong>Querying</strong> the <code>i<sup>th</sup></code> <strong>best</strong> location of <strong>all
 * 	locations already added</strong>, where <code>i</code> is the number of times the system has been queried
 * 	(including the current query).
 * 	<ul>
 * 		<li>For example, when the system is queried for the <code>4<sup>th</sup></code> time, it returns the
 * 		<code>4<sup>th</sup></code> best location of all locations already added.</li>
 * 	</ul>
 * 	</li>
 * </ul>
 *
 * <p>Note that the test data are generated so that <strong>at any time</strong>, the number of queries <strong>does
 * not exceed</strong> the number of locations added to the system.</p>
 *
 * <p>Implement the <code>SORTracker</code> class:</p>
 *
 * <ul>
 * 	<li><code>SORTracker()</code> Initializes the tracker system.</li>
 * 	<li><code>void add(string name, int score)</code> Adds a scenic location with <code>name</code> and
 * 	<code>score</code> to the system.</li>
 * 	<li><code>string get()</code> Queries and returns the <code>i<sup>th</sup></code> best location, where
 * 	<code>i</code> is the number of times this method has been invoked (including this invocation).</li>
 * </ul>
 *
 * <p>&nbsp;</p>
 * <p><strong class="example">Example 1:</strong></p>
 *
 * <pre>
 * <strong>Input</strong>
 * [&quot;SORTracker&quot;, &quot;add&quot;, &quot;add&quot;, &quot;get&quot;, &quot;add&quot;, &quot;get&quot;, &quot;add&quot;, &quot;get&quot;, &quot;add&quot;, &quot;get&quot;, &quot;add&quot;, &quot;get&quot;, &quot;get&quot;]
 * [[], [&quot;bradford&quot;, 2], [&quot;branford&quot;, 3], [], [&quot;alps&quot;, 2], [], [&quot;orland&quot;, 2], [], [&quot;orlando&quot;, 3], [], [&quot;alpine&quot;, 2], [], []]
 * <strong>Output</strong>
 * [null, null, null, &quot;branford&quot;, null, &quot;alps&quot;, null, &quot;bradford&quot;, null, &quot;bradford&quot;, null, &quot;bradford&quot;, &quot;orland&quot;]
 *
 * <strong>Explanation</strong>
 * SORTracker tracker = new SORTracker(); // Initialize the tracker system.
 * tracker.add(&quot;bradford&quot;, 2); // Add location with name=&quot;bradford&quot; and score=2 to the system.
 * tracker.add(&quot;branford&quot;, 3); // Add location with name=&quot;branford&quot; and score=3 to the system.
 * tracker.get();              // The sorted locations, from best to worst, are: branford, bradford.
 *                             // Note that branford precedes bradford due to its <strong>higher score</strong> (3 &gt; 2).
 *                             // This is the 1<sup>st</sup> time get() is called, so return the best location: &quot;branford&quot;.
 * tracker.add(&quot;alps&quot;, 2);     // Add location with name=&quot;alps&quot; and score=2 to the system.
 * tracker.get();              // Sorted locations: branford, alps, bradford.
 *                             // Note that alps precedes bradford even though they have the same score (2).
 *                             // This is because &quot;alps&quot; is <strong>lexicographically smaller</strong> than &quot;bradford&quot;.
 *                             // Return the 2<sup>nd</sup> best location &quot;alps&quot;, as it is the 2<sup>nd</sup> time get() is called.
 * tracker.add(&quot;orland&quot;, 2);   // Add location with name=&quot;orland&quot; and score=2 to the system.
 * tracker.get();              // Sorted locations: branford, alps, bradford, orland.
 *                             // Return &quot;bradford&quot;, as it is the 3<sup>rd</sup> time get() is called.
 * tracker.add(&quot;orlando&quot;, 3);  // Add location with name=&quot;orlando&quot; and score=3 to the system.
 * tracker.get();              // Sorted locations: branford, orlando, alps, bradford, orland.
 *                             // Return &quot;bradford&quot;.
 * tracker.add(&quot;alpine&quot;, 2);   // Add location with name=&quot;alpine&quot; and score=2 to the system.
 * tracker.get();              // Sorted locations: branford, orlando, alpine, alps, bradford, orland.
 *                             // Return &quot;bradford&quot;.
 * tracker.get();              // Sorted locations: branford, orlando, alpine, alps, bradford, orland.
 *                             // Return &quot;orland&quot;.
 * </pre>
 *
 * <p>&nbsp;</p>
 * <p><strong>Constraints:</strong></p>
 *
 * <ul>
 * 	<li><code>name</code> consists of lowercase English letters, and is unique among all locations.</li>
 * 	<li><code>1 &lt;= name.length &lt;= 10</code></li>
 * 	<li><code>1 &lt;= score &lt;= 10<sup>5</sup></code></li>
 * 	<li>At any time, the number of calls to <code>get</code> does not exceed the number of calls to <code>add</code>
 * 	.</li>
 * 	<li>At most <code>4 * 10<sup>4</sup></code> calls <strong>in total</strong> will be made to <code>add</code> and
 * 	<code>get</code>.</li>
 * </ul>
 */
public class Q2102_SequentiallyOrdinalRankTracker {

    private static class SORTracker {

        static class Location implements Comparable<Location> {

            final String name;

            final int score;

            Location(String name, int score) {
                this.name = name;
                this.score = score;
            }

            @Override
            public int compareTo(Location o) {
                if (this.score == o.score) {
                    return this.name.compareTo(o.name);
                }
                return o.score - this.score;
            }
        }

        // 未访问过的地点
        PriorityQueue<Location> unGetted = new PriorityQueue<>();

        // 已访问过的地点
        PriorityQueue<Location> getted = new PriorityQueue<>(Comparator.reverseOrder());

        public SORTracker() {

        }

        public void add(String name, int score) {
            Location location = new Location(name, score);
            if (getted.isEmpty() || location.compareTo(getted.peek()) > 0) {
                // 比已访问过的排序低, 放到待访问队列
                unGetted.offer(location);
            } else {
                // 比已访问过的排序高, 就将已访问过的最低的放回未访问过的, 新添加的放到已访问的
                Location top = getted.poll();
                unGetted.offer(top);
                getted.offer(location);
            }
        }

        public String get() {
            Location top = unGetted.poll();
            getted.offer(top);
            return top.name;
        }
    }

    /**
     * Your SORTracker object will be instantiated and called as such:
     * SORTracker obj = new SORTracker();
     * obj.add(name,score);
     * String param_2 = obj.get();
     */
    @Test
    public void testMethod() {
        String ret;
        // Initialize the tracker system.
        SORTracker tracker = new SORTracker();

        // Add location with name="bradford" and score=2 to the system.
        tracker.add("bradford", 2);
        // Add location with name="branford" and score=3 to the system.
        tracker.add("branford", 3);
        // The sorted locations, from best to worst, are: branford, bradford.
        ret = tracker.get();
        // Note that branford precedes bradford due to its higher score (3 > 2).
        // This is the 1st time get() is called, so return the best location: "branford".
        Assert.assertEquals("branford", ret);

        // Add location with name="alps" and score=2 to the system.
        tracker.add("alps", 2);
        // Sorted locations: branford, alps, bradford.
        ret = tracker.get();
        // Note that alps precedes bradford even though they have the same score (2).
        // This is because "alps" is lexicographically smaller than "bradford".
        // Return the 2nd best location "alps", as it is the 2nd time get() is called.
        Assert.assertEquals("alps", ret);

        // Add location with name="orland" and score=2 to the system.
        tracker.add("orland", 2);
        // Sorted locations: branford, alps, bradford, orland.
        ret = tracker.get();
        // Return "bradford", as it is the 3rd time get() is called.
        Assert.assertEquals("bradford", ret);

        // Add location with name="orlando" and score=3 to the system.
        tracker.add("orlando", 3);
        // Sorted locations: branford, orlando, alps, bradford, orland.
        ret = tracker.get();
        // Return "bradford".
        Assert.assertEquals("bradford", ret);

        // Add location with name="alpine" and score=2 to the system.
        tracker.add("alpine", 2);
        // Sorted locations: branford, orlando, alpine, alps, bradford, orland.
        ret = tracker.get();
        // Return "bradford".
        Assert.assertEquals("bradford", ret);

        // Sorted locations: branford, orlando, alpine, alps, bradford, orland.
        ret = tracker.get();
        // Return "orland".
        Assert.assertEquals("orland", ret);
    }

}
