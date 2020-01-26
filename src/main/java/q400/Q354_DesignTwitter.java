package q400;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;
import org.junit.Test;
import util.asserthelper.AssertUtils;

/**
 * https://leetcode.com/problems/design-twitter/
 *
 * Design a simplified version of Twitter where users can post tweets, follow/unfollow another user and is able to
 * see the 10 most recent tweets in the user's news feed. Your design should support the following methods:
 *
 * postTweet(userId, tweetId): Compose a new tweet.
 * getNewsFeed(userId): Retrieve the 10 most recent tweet ids in the user's news feed. Each item in the news feed
 * must be posted by users who the user followed or by the user herself. Tweets must be ordered from most recent
 * to least recent.
 * follow(followerId, followeeId): Follower follows a followee.
 * unfollow(followerId, followeeId): Follower unfollows a followee.
 *
 * Example:
 *
 * Twitter twitter = new Twitter();
 *
 * // User 1 posts a new tweet (id = 5).
 * twitter.postTweet(1, 5);
 *
 * // User 1's news feed should return a list with 1 tweet id -> [5].
 * twitter.getNewsFeed(1);
 *
 * // User 1 follows user 2.
 * twitter.follow(1, 2);
 *
 * // User 2 posts a new tweet (id = 6).
 * twitter.postTweet(2, 6);
 *
 * // User 1's news feed should return a list with 2 tweet ids -> [6, 5].
 * // Tweet id 6 should precede tweet id 5 because it is posted after tweet id 5.
 * twitter.getNewsFeed(1);
 *
 * // User 1 unfollows user 2.
 * twitter.unfollow(1, 2);
 *
 * // User 1's news feed should return a list with 1 tweet id -> [5],
 * // since user 1 is no longer following user 2.
 * twitter.getNewsFeed(1);
 */
public class Q354_DesignTwitter {

    private static class Twitter {

        private Map<Integer, Set<Integer>> follows = new HashMap<>();

        private Map<Integer, News> userNews = new HashMap<>();

        private static final int LIMIT = 10;

        private int nextSequence = 0;

        private static class News {

            int sequence;

            int tweetId;

            News next;
        }

        /**
         * Initialize your data structure here.
         */
        public Twitter() {

        }

        /**
         * Compose a new tweet.
         */
        public void postTweet(int userId, int tweetId) {
            News prevNews = userNews.get(userId);
            News newNews = new News();
            newNews.next = prevNews;
            newNews.sequence = nextSequence++;
            newNews.tweetId = tweetId;
            userNews.put(userId, newNews);
        }

        /**
         * Retrieve the 10 most recent tweet ids in the user's news feed. Each item in the news feed must be posted by
         * users who the user followed or by the user herself. Tweets must be ordered from most recent to least recent.
         */
        public List<Integer> getNewsFeed(int userId) {
            Set<Integer> follow = follows.getOrDefault(userId, Collections.emptySet());
            List<Integer> res = new ArrayList<>(LIMIT);
            PriorityQueue<News> queue = new PriorityQueue<>(follow.size() + 1, (a, b) -> b.sequence - a.sequence);
            for (Integer followeeId : follow) {
                News news = userNews.get(followeeId);
                if (news != null) {
                    queue.add(news);
                }
            }
            if (!follow.contains(userId)) {
                News news = userNews.get(userId);
                if (news != null) {
                    queue.add(news);
                }
            }
            for (int i = 0; i < LIMIT; i++) {
                News news = queue.poll();
                if (news == null) {
                    break;
                }
                res.add(news.tweetId);
                if (news.next != null) {
                    queue.add(news.next);
                }
            }
            return res;
        }

        /**
         * Follower follows a followee. If the operation is invalid, it should be a no-op.
         */
        public void follow(int followerId, int followeeId) {
            Set<Integer> follow = follows.computeIfAbsent(followerId, k -> new HashSet<>());
            follow.add(followeeId);
        }

        /**
         * Follower unfollows a followee. If the operation is invalid, it should be a no-op.
         */
        public void unfollow(int followerId, int followeeId) {
            Set<Integer> follow = follows.computeIfAbsent(followerId, k -> new HashSet<>());
            follow.remove(followeeId);
        }
    }

    @Test
    public void testMethod() {
        List<Integer> newsFeed;

        Twitter twitter = new Twitter();
        twitter.postTweet(1, 5);
        newsFeed = twitter.getNewsFeed(1);
        AssertUtils.assertEquals(new int[]{5}, newsFeed);

        twitter.follow(1, 2);
        twitter.postTweet(2, 6);
        newsFeed = twitter.getNewsFeed(1);
        AssertUtils.assertEquals(new int[]{6, 5}, newsFeed);

        twitter.unfollow(1, 2);
        newsFeed = twitter.getNewsFeed(1);
        AssertUtils.assertEquals(new int[]{5}, newsFeed);
    }

}
