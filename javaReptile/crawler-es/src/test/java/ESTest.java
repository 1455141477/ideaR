import cn.itcast.pojo.Item;
import cn.itcast.service.ItemService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class ESTest {
    //创建索引和映射
    //新增
    //修改
    //删除
    @Autowired
    private ItemService itemService;
    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;

    @Test
    public void createIndex() {
        this.elasticsearchTemplate.createIndex(Item.class);
        this.elasticsearchTemplate.putMapping(Item.class);
    }

    @Test
    public void testSave() {
        Item item = new Item();
        item.setId(100);
        item.setTitle("SpringData Es");
        item.setContent("sdjfiasdfsdf");
        itemService.save(item);
    }

    /**
     * 和添加代码一样如果存在就是修改
     */
    @Test
    public void testUpdate() {
        Item item = new Item();
        item.setId(1000);
        item.setTitle("SpringData");
        item.setContent("sdfs");
        itemService.save(item);
    }

    /**
     * 删除
     */
    @Test
    public void testDelete() {
        Item item = new Item();
        item.setId(100);
        itemService.delete(item);
    }

    /**
     * 批量保存
     */
    public void testSaveAll() {
        List<Item> list = new ArrayList<Item>();
        for (int i = 0; i < 100; i++) {
            Item item = new Item();
            item.setId(1000);
            item.setTitle("SpringData");
            item.setContent("sdfs");
            list.add(item);
        }
        itemService.saveAll(list);
    }

    /**
     * 简单查询 查询所有数据
     */
    public void testfindAll() {
        Iterable<Item> items = itemService.findAll();
        Iterator<Item> iterator = items.iterator();
        while (iterator.hasNext()) {
            Item item = iterator.next();
            System.out.println(item.getTitle());
            System.out.println(item.getContent());
            System.out.println(item.getId());
        }
    }

    /**
     * 分页查询
     */
    public void testfindByPage() {
        Page<Item> page = this.itemService.findByPage(1, 30);
        for (Item item : page.getContent()) {
            System.out.println(item);
        }
    }

    /**
     * 复杂查询
     * 交集
     */
    public void testfindByTitleAndContent() {
        List<Item> list = this.itemService.findByTitleAndContent("今日", "姓名");
        for (Item item : list) {
            System.out.println(item.toString());
        }
    }

    /**
     * 复杂查询之分页查询
     */
    public void testfindByTitleOrContent() {
        Page<Item> items = this.itemService.findByTitleOrContent("今日", "姓名", 1, 5);
        for (Item item : items.getContent()) {
            System.out.println(item.toString());
        }
    }

    /**
     * 根据title或者content和id的范围，进行分页查询
     */
    public void testfindByTitleAndContentAndIdBetween() {
        this.itemService.findByTitleAndContentAndIdBetween("今日", "姓名", 10, 20,1,5);
    }
}
