package cn.itcast.service.impl;

import cn.itcast.dao.ItemRepository;
import cn.itcast.pojo.Item;
import cn.itcast.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ItemServiceImpl implements ItemService {
    @Autowired
    private ItemRepository itemRepository;

    public void save(Item item) {
        itemRepository.save(item);
    }

    public void delete(Item item) {
        itemRepository.delete(item);
    }

    public Iterable<Item> findAll() {
        Iterable<Item> items = itemRepository.findAll();
        return items;
    }

    public void saveAll(List<Item> list) {
        itemRepository.saveAll(list);
    }

    public Page<Item> findByTitleOrContent(String title, String content, int m, int n) {
        return itemRepository.findByTitleOrContent(title, content, PageRequest.of(m, n));
    }

    public Page<Item> findByPage(int page, int rows) {
        Page<Item> items = this.itemRepository.findAll(PageRequest.of(page, rows));
        return items;
    }

    public Page<Item> findByTitleAndContentAndIdBetween(String title, String content, int min, int max, int m, int n) {
        return this.itemRepository.findByTitleAndContentAndIdBetween(title,content,min,max,PageRequest.of(m,n));
    }

    public List<Item> findByTitleAndContent(String title, String content) {
        return itemRepository.findByTitleAndContent(title,content);
    }
}
