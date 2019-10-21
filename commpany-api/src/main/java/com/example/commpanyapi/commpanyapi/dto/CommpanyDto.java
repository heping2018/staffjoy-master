package com.example.commpanyapi.commpanyapi.dto;


import com.example.commonlib.commonlib.validation.DayOfWeek;
import com.example.commonlib.commonlib.validation.Group1;
import com.example.commonlib.commonlib.validation.Group2;
import com.example.commonlib.commonlib.validation.Timezone;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CommpanyDto {
    @NotBlank(groups = {Group1.class})
    private String id;
    @NotBlank(groups ={Group1.class,Group2.class})
    private String name;
    @Timezone(groups={Group1.class,Group2.class})
    @NotBlank(groups={Group1.class,Group2.class})
    private String defaultTimeZone;
    @DayOfWeek(groups = {Group1.class,Group2.class})
    @NotBlank(groups={Group2.class,Group1.class})
    private String defaultDayWeekStart;
    private boolean archived;
}
