/*
 * Copyright 2024-2026 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.alibaba.cloud.ai.dataagent.service.vectorstore;

import com.alibaba.cloud.ai.dataagent.dto.search.AgentSearchRequest;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.filter.Filter;

import java.util.List;
import java.util.Map;

public interface AgentVectorStoreService {

	/**
	 * 查询某个Agent的文档 总入口
	 */
	List<Document> search(AgentSearchRequest searchRequest);

	Boolean deleteDocumentsByVectorType(String agentId, String vectorType) throws Exception;

	Boolean deleteDocumentsByMetedata(String agentId, Map<String, Object> metadata);

	Boolean deleteDocumentsByMetadata(Map<String, Object> metadata);

	/**
	 * Get documents for specified agent
	 */
	List<Document> getDocumentsForAgent(String agentId, String query, String vectorType);

	List<Document> getDocumentsForAgent(String agentId, String query, String vectorType, int topK, double threshold);

	// 通过元数据过滤精确查找
	List<Document> getDocumentsOnlyByFilter(Filter.Expression filterExpression, Integer topK);

	/**
	 * 带自定义过滤表达式的语义搜索。与 getDocumentsOnlyByFilter 不同，此方法使用真实的 query
	 * 进行向量相似度排序，而非仅做元数据过滤。
	 * @param query 用户查询文本，用于向量相似度匹配
	 * @param filterExpression 自定义过滤表达式（如 datasourceId + vectorType 约束）
	 * @param topK 返回的最大文档数
	 * @param similarityThreshold 相似度阈值
	 * @return 按相似度排序的文档列表
	 */
	List<Document> searchWithFilter(String query, Filter.Expression filterExpression, int topK,
			double similarityThreshold);

	boolean hasDocuments(String agentId);

	void addDocuments(String agentId, List<Document> documents);

}
