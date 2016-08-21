package com.rightminds.biller.service;

import com.rightminds.biller.entity.Bill;
import com.rightminds.biller.entity.BillItem;
import com.rightminds.biller.util.JsonUtil;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;

@Service
public class ElasticSearchService {

    private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(ElasticSearchService.class);

    private Client client;

    @PostConstruct
    public void initialize() throws UnknownHostException {
        Map<String, String> map = new HashMap<>();
        map.put("cluster.name", "elasticsearch_thirur");
        map.put("client.transport.ignore_cluster_name", "false");
        map.put("node.client", "true");
        map.put("client.transport.sniff", "true");
        Settings settings = Settings.builder().put(map).build();
        client = new TransportClient.Builder()
                .settings(settings).build()
                .addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("localhost"), 9300));

    }

    public void save(Bill bill) {
        IndexRequest indexRequest = new IndexRequest("bill", "bill", bill.getId().toString());
        String source = JsonUtil.toJson(bill);
        indexRequest.source(source);
        IndexResponse response = client.index(indexRequest).actionGet();
        if (!response.isCreated()) {
            LOGGER.error("Problem in saving data in elasticsearch: {}", source);
            throw new RuntimeException("Data not created");
        }
    }

    public void save(BillItem billItem) {
        IndexRequest indexRequest = new IndexRequest("billitem", "billitem", billItem.getId().toString());
        String source = JsonUtil.toJson(billItem);
        indexRequest.source(source);
        IndexResponse response = client.index(indexRequest).actionGet();
        if (!response.isCreated()) {
            LOGGER.error("Problem in saving data in elasticsearch: {}", source);
            throw new RuntimeException("Data not created");
        }
    }
}
