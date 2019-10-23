package com.example.commpanyapi.commpanyapi.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommpanyList {
    private List<CommpanyDto> commpanyDtos;
    private int offset;
    private int limit;
}
