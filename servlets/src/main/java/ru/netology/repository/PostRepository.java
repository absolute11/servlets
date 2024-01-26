package ru.netology.repository;

import ru.netology.model.Post;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public class PostRepository {
  private final Map<Long, Post> posts;

  public PostRepository() {
    this.posts = new ConcurrentHashMap<>();
  }

  public Optional<Post> getById(long id) {
    return Optional.ofNullable(posts.get(id));
  }

  public Post save(Post post) {
    if (post.getId() == 0) {
      post.setId(posts.size() + 1);
    }
    posts.put(post.getId(), post);
    return post;
  }

  public void removeById(long id) {
    posts.remove(id);
  }
}