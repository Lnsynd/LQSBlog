package com.lqs.job;

import com.lqs.domain.entity.Article;
import com.lqs.service.ArticleService;
import com.lqs.utils.RedisCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class UpdateViewCountJob {

    @Autowired
    RedisCache redisCache;

    @Autowired
    private ArticleService articleService;


    @Scheduled(cron = "* 10 * * * ?")
    public void updateViewCount() {
        // 获取redis浏览量
        Map<String, Integer> viewCountMap = redisCache.getCacheMap("article:viewCount");

        // Map转换成List
        List<Article> articles = viewCountMap.entrySet().stream()
                .map(entry -> new Article(Long.valueOf(entry.getKey()), entry.getValue().longValue()))
                .collect(Collectors.toList());

        // 更新
        articleService.updateBatchById(articles);
    }
}
