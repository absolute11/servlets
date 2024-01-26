package ru.netology.repository;

import ru.netology.model.Post;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

public class PostRepository {
  private final List<Post> posts = new ArrayList<>();
  private final AtomicLong idGenerator = new AtomicLong(0);

  public List<Post> all() {
    return posts;
  }

  public Post getById(long id) {
    return posts.stream()
            .filter(post -> post.getId() == id)
            .findFirst()
            .orElse(null);
  }

  public synchronized Post save(Post post) {
    if (post.getId() == 0) {
      post.setId(idGenerator.incrementAndGet());
      posts.add(post);
    } else {
      posts.stream()
              .filter(p -> p.getId() == post.getId())
              .findFirst()
              .ifPresent(existingPost -> {
                existingPost.setContent(post.getContent());
              });
    }
    return post;
  }

  public synchronized void removeById(long id) {
    posts.removeIf(post -> post.getId() == id);
  }
}