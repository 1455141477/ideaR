package cn.itcast.service;

import cn.itcast.pojo.Item;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ItemService {
    public void save(Item item);

    public void delete(Item item);

    public Iterable<Item> findAll();

    public void saveAll(List<Item> list);

    public Page<Item> findByPage(int page, int rows);

    List<Item> findByTitleAndContent(String title, String content);

    Page<Item> findByTitleOrContent(String title, String content, int m, int n);

    Page<Item> findByTitleAndContentAndIdBetween(String title, String content, int min, int max, int m,int n);
}
