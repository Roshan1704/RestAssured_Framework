package com.api.automation.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * POJO model for User List API response
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserListResponse {
    
    @JsonProperty("page")
    private int page;
    
    @JsonProperty("per_page")
    private int perPage;
    
    @JsonProperty("total")
    private int total;
    
    @JsonProperty("total_pages")
    private int totalPages;
    
    @JsonProperty("data")
    private List<User> data;
    
    @JsonProperty("support")
    private Support support;
}
