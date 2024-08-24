package q2150;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * <h3>[Medium] 2115. Find All Possible Recipes from Given Supplies</h3>
 * <a href="https://leetcode.com/problems/find-all-possible-recipes-from-given-supplies/">
 * https://leetcode.com/problems/find-all-possible-recipes-from-given-supplies/
 * </a><br/>
 *
 * <p>You have information about <code>n</code> different recipes. You are given a string array <code>recipes</code>
 * and
 * a 2D string array <code>ingredients</code>. The <code>i<sup>th</sup></code> recipe has the name
 * <code>recipes[i]</code>, and you can <strong>create</strong> it if you have <strong>all</strong> the needed
 * ingredients from <code>ingredients[i]</code>. Ingredients to a recipe may need to be created from <strong>other
 * </strong>recipes, i.e., <code>ingredients[i]</code> may contain a string that is in <code>recipes</code>.</p>
 *
 * <p>You are also given a string array <code>supplies</code> containing all the ingredients that you initially have,
 * and you have an infinite supply of all of them.</p>
 *
 * <p>Return <em>a list of all the recipes that you can create. </em>You may return the answer in <strong>any
 * order</strong>.</p>
 *
 * <p>Note that two recipes may contain each other in their ingredients.</p>
 *
 * <p>&nbsp;</p>
 * <p><strong class="example">Example 1:</strong></p>
 *
 * <pre>
 * <strong>Input:</strong> recipes = [&quot;bread&quot;], ingredients = [[&quot;yeast&quot;,&quot;flour&quot;]], supplies = [&quot;yeast&quot;,&quot;flour&quot;,&quot;corn&quot;]
 * <strong>Output:</strong> [&quot;bread&quot;]
 * <strong>Explanation:</strong>
 * We can create &quot;bread&quot; since we have the ingredients &quot;yeast&quot; and &quot;flour&quot;.
 * </pre>
 *
 * <p><strong class="example">Example 2:</strong></p>
 *
 * <pre>
 * <strong>Input:</strong> recipes = [&quot;bread&quot;,&quot;sandwich&quot;], ingredients = [[&quot;yeast&quot;,&quot;flour&quot;],[&quot;bread&quot;,&quot;meat&quot;]], supplies = [&quot;yeast&quot;,&quot;flour&quot;,&quot;meat&quot;]
 * <strong>Output:</strong> [&quot;bread&quot;,&quot;sandwich&quot;]
 * <strong>Explanation:</strong>
 * We can create &quot;bread&quot; since we have the ingredients &quot;yeast&quot; and &quot;flour&quot;.
 * We can create &quot;sandwich&quot; since we have the ingredient &quot;meat&quot; and can create the ingredient &quot;bread&quot;.
 * </pre>
 *
 * <p><strong class="example">Example 3:</strong></p>
 *
 * <pre>
 * <strong>Input:</strong> recipes = [&quot;bread&quot;,&quot;sandwich&quot;,&quot;burger&quot;], ingredients = [[&quot;yeast&quot;,&quot;flour&quot;],[&quot;bread&quot;,&quot;meat&quot;],[&quot;sandwich&quot;,&quot;meat&quot;,&quot;bread&quot;]], supplies = [&quot;yeast&quot;,&quot;flour&quot;,&quot;meat&quot;]
 * <strong>Output:</strong> [&quot;bread&quot;,&quot;sandwich&quot;,&quot;burger&quot;]
 * <strong>Explanation:</strong>
 * We can create &quot;bread&quot; since we have the ingredients &quot;yeast&quot; and &quot;flour&quot;.
 * We can create &quot;sandwich&quot; since we have the ingredient &quot;meat&quot; and can create the ingredient &quot;bread&quot;.
 * We can create &quot;burger&quot; since we have the ingredient &quot;meat&quot; and can create the ingredients &quot;bread&quot; and &quot;sandwich&quot;.
 * </pre>
 *
 * <p>&nbsp;</p>
 * <p><strong>Constraints:</strong></p>
 *
 * <ul>
 * 	<li><code>n == recipes.length == ingredients.length</code></li>
 * 	<li><code>1 &lt;= n &lt;= 100</code></li>
 * 	<li><code>1 &lt;= ingredients[i].length, supplies.length &lt;= 100</code></li>
 * 	<li><code>1 &lt;= recipes[i].length, ingredients[i][j].length, supplies[k].length &lt;= 10</code></li>
 * 	<li><code>recipes[i], ingredients[i][j]</code>, and <code>supplies[k]</code> consist only of lowercase English
 * 	letters.</li>
 * 	<li>All the values of <code>recipes</code> and <code>supplies</code>&nbsp;combined are unique.</li>
 * 	<li>Each <code>ingredients[i]</code> does not contain any duplicate values.</li>
 * </ul>
 */
@RunWith(LeetCodeRunner.class)
public class Q2115_FindAllPossibleRecipesFromGivenSupplies {

    @Answer
    public List<String> findAllRecipes(String[] recipes, List<List<String>> ingredients, String[] supplies) {
        final int n = recipes.length;
        Map<String, Node> nodes = new HashMap<>();
        Queue<Node> queue = new ArrayDeque<>();
        for (String supply : supplies) {
            Node node = new Node();
            node.name = supply;
            nodes.put(supply, node);
            node.visited = true;
            queue.offer(node);
        }
        for (int i = 0; i < n; i++) {
            Node node = new Node();
            node.name = recipes[i];
            node.isRecipe = true;
            nodes.put(recipes[i], node);
        }
        for (int i = 0; i < n; i++) {
            Node recipe = nodes.get(recipes[i]);
            recipe.inDegree = ingredients.get(i).size();
            for (String ingredientName : ingredients.get(i)) {
                Node ingredient = nodes.get(ingredientName);
                if (ingredient != null) {
                    ingredient.out.add(recipe);
                }
            }
        }
        while (!queue.isEmpty()) {
            Node node = queue.poll();
            for (Node next : node.out) {
                next.inDegree--;
                if (!next.visited && next.inDegree == 0) {
                    next.visited = true;
                    queue.offer(next);
                }
            }
        }

        List<String> res = new ArrayList<>();
        for (Node node : nodes.values()) {
            if (node.isRecipe && node.visited) {
                res.add(node.name);
            }
        }
        return res;
    }

    private static class Node {

        // 是否是食谱
        boolean isRecipe;

        // 是否在遍历中被访问到
        boolean visited;

        // 食物/食谱名称
        String name;

        // 节点入度
        int inDegree;

        // 可以参与制作的食谱
        List<Node> out = new ArrayList<>();

    }

    @TestData
    public DataExpectation example1 = DataExpectation
            .createWith(new String[]{"bread"}, List.of(List.of("yeast", "flour")),
                    new String[]{"yeast", "flour", "corn"})
            .expect(List.of("bread"));

    @TestData
    public DataExpectation example2 = DataExpectation
            .createWith(new String[]{"bread", "sandwich"}, List.of(List.of("yeast", "flour"), List.of("bread", "meat")),
                    new String[]{"yeast", "flour", "meat"})
            .expect(List.of("bread", "sandwich"))
            .unOrder();

    @TestData
    public DataExpectation example3 = DataExpectation
            .createWith(new String[]{"bread", "sandwich", "burger"},
                    List.of(List.of("yeast", "flour"), List.of("bread", "meat"), List.of("sandwich", "meat", "bread")),
                    new String[]{"yeast", "flour", "meat"})
            .expect(List.of("bread", "sandwich", "burger"))
            .unOrder();

    @TestData
    public DataExpectation normal1 = DataExpectation
            .createWith(new String[]{"bread"},
                    List.of(List.of("yeast", "flour")),
                    new String[]{"yeast"})
            .expect(List.of());

}
