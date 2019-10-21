package com.example.commpanyapi.commpanyapi.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DIrectoryList {
    private int limit;
    private int offset;
    private List<DirectoryEntryDto> accounts = new ArrayList<>();

}
