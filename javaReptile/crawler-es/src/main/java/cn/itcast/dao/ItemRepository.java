package cn.itcast.dao;

import cn.itcast.pojo.Item;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

public interface ItemRepository extends ElasticsearchRepository<Item, Integer> {
    List<Item> findByTitleAndContent(String title, String content);

    Page<Item> findByTitleOrContent(String title, String content, PageRequest of);

    Page<Item> findByTitleAndContentAndIdBetween(String title, String content, int min, int max, PageRequest of);
}
