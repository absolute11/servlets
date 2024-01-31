package ru.netology.servlet;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import ru.netology.config.Appconfig;
import ru.netology.controller.PostController;
import ru.netology.repository.PostRepository;
import ru.netology.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;

public class MainServlet extends HttpServlet {
  private static final String API_POSTS = "/api/posts";
  private static final String APPLICATION_JSON = "application/json";
  private static final String DELETE_METHOD = "DELETE";
  private static final String GET_METHOD = "GET";
  private static final String POST_METHOD = "POST";

  private final PostController controller;
  public MainServlet() {
    // Создаем контекст Spring с помощью конфигурационного класса AppConfig
    AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(Appconfig.class);
    // Получаем экземпляр PostController из контекста Spring
    controller = context.getBean(PostController.class);
  }

  @Override
  protected void service(HttpServletRequest req, HttpServletResponse resp) throws IOException {
    try {
      final var path = req.getRequestURI();
      final var method = req.getMethod();

      if (method.equals(GET_METHOD) && path.equals(API_POSTS)) {
        controller.all(resp);
        return;
      }
      if (method.equals(GET_METHOD) && path.matches("/api/posts/\\d+")) {
        final var id = Long.parseLong(path.substring(path.lastIndexOf("/")));
        controller.getById(id, resp);
        return;
      }
      if (method.equals(POST_METHOD) && path.equals(API_POSTS)) {
        controller.save(req.getReader(), resp);
        return;
      }
      if (method.equals(DELETE_METHOD) && path.matches("/api/posts/\\d+")) {
        final var id = Long.parseLong(path.substring(path.lastIndexOf("/")));
        controller.removeById(id, resp);
        return;
      }
      resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
    } catch (Exception e) {
      e.printStackTrace();
      resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
    }
  }
}