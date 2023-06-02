package com.example.vpdomaci5.repository;

import com.example.vpdomaci5.model.Comment;
import com.example.vpdomaci5.model.Post;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class InMemoryPostRepository implements PostRepository {
    private final AtomicLong id = new AtomicLong();
    private final ReadWriteLock lock = new ReentrantReadWriteLock();
    private final List<Post> posts = new CopyOnWriteArrayList<>();

    public InMemoryPostRepository() {
        init();
    }

    @Override
    public Post save(Post post) {
        lock.writeLock().lock();
        try {
            if (!posts.contains(post)) {
                post.setId(id.getAndIncrement());
                posts.add(post);
            }
            return post;
        } finally {
            lock.writeLock().unlock();
        }
    }

    @Override
    public List<Post> getAllPosts() {
        lock.readLock().lock();
        try {
            return new ArrayList<>(posts);
        } finally {
            lock.readLock().unlock();
        }
    }

    @Override
    public Post getPostById(long id) {
        lock.readLock().lock();
        try {
            return posts.stream()
                    .filter(post -> post.getId() == id)
                    .findFirst()
                    .orElse(null);
        } finally {
            lock.readLock().unlock();
        }
    }

    @Override
    public void addComment(long postId, Comment comment) {
        lock.writeLock().lock();
        try {
            posts.stream()
                    .filter(post -> post.getId() == postId)
                    .findFirst()
                    .map(post -> post.getComments().add(comment));
        } finally {
            lock.writeLock().unlock();
        }
    }

    @Override
    public List<Comment> getPostComments(long postId) {
        lock.readLock().lock();
        try {
            return new ArrayList<>(
                    posts.stream()
                            .filter(post -> post.getId() == postId)
                            .findFirst()
                            .map(Post::getComments)
                            .orElse(new ArrayList<>())
            );
        } finally {
            lock.readLock().unlock();
        }
    }

    private void init() {
        Post p1 = new Post("John Smith", "The Physics of Time Travel", "Time travel has long been a topic of science fiction, but what would it really take to travel through time? According to the theory of relativity, time dilation can occur at high speeds or near massive objects. This means that time can pass at different rates for different observers. To truly travel through time, we would need to find a way to manipulate time dilation and possibly create a time machine. However, many physicists believe that the laws of physics prevent time travel, and we may never be able to achieve it.");
        Post p2 = new Post("Sarah Jones", "The Benefits of Exercise for Mental Health", "We all know that exercise is important for physical health, but did you know it's also crucial for mental health? Studies have shown that regular exercise can help reduce symptoms of depression and anxiety, boost self-esteem, and improve cognitive function. Exercise releases endorphins, which are natural mood-boosters, and can also reduce stress and improve sleep quality. So, next time you're feeling down, try going for a walk or hitting the gym.");
        Post p3 = new Post("Michael Brown", "The Psychology of Procrastination", "We've all been there - putting off tasks until the last minute, even though we know we shouldn't. But why do we procrastinate? According to psychologists, it's often because we feel overwhelmed by the task at hand or fear failure. Procrastination can also be a way of avoiding unpleasant tasks or seeking instant gratification. To overcome procrastination, try breaking tasks down into smaller, manageable steps and setting realistic goals. It's also important to recognize and address the underlying causes of procrastination.");
        Post p4 = new Post("Lisa Chen", "The Science of Sleep", "Sleep is essential for our physical and mental health, but many of us don't get enough of it. The science of sleep is complex, but we do know that it plays a crucial role in memory consolidation, learning, and emotional regulation. Lack of sleep can lead to a range of negative health effects, from weight gain to decreased immune function. To improve your sleep, try sticking to a regular sleep schedule, avoiding caffeine and electronics before bedtime, and creating a relaxing sleep environment.");

        Comment c1 = new Comment("Jane Doe", "While it's true that time travel seems far-fetched, I think it's important to keep exploring the possibilities. Who knows what we might discover in the future?");
        Comment c2 = new Comment("Bob Johnson", " I agree with John that the laws of physics may prevent time travel, but it's still fascinating to think about the implications if it were possible.");
        Comment c3 = new Comment("Tom Lee", "I can definitely attest to the benefits of exercise for mental health. Whenever I'm feeling stressed, a good workout always helps me feel better.");
        Comment c4 = new Comment("Emily Nguyen", "I never realized how much exercise could impact my mental health until I started a regular routine. It's amazing how much of a difference it can make.");
        Comment c5 = new Comment("Rachel Patel", "As someone who struggles with procrastination, I appreciate the tips for overcoming it. Breaking tasks down into smaller steps has been especially helpful for me.");
        Comment c6 = new Comment("David Kim", " It's interesting to think about why we procrastinate and how we can overcome it. I think recognizing the underlying causes is key to making lasting changes.");
        Comment c7 = new Comment("Jason Lee", "I've struggled with insomnia for years, so I appreciate the tips for improving sleep. Creating a calming sleep environment has made.");

        p1.getComments().add(c1);
        p1.getComments().add(c2);
        p2.getComments().add(c3);
        p2.getComments().add(c4);
        p3.getComments().add(c5);
        p3.getComments().add(c6);
        p4.getComments().add(c7);

        p1.setId(id.getAndIncrement());
        p2.setId(id.getAndIncrement());
        p3.setId(id.getAndIncrement());
        p4.setId(id.getAndIncrement());

        lock.writeLock().lock();
        try {
            posts.add(p1);
            posts.add(p2);
            posts.add(p3);
            posts.add(p4);
        } finally {
            lock.writeLock().unlock();
        }
    }
}
