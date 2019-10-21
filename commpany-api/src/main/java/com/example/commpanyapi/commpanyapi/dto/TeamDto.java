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
import javax.validation.constraints.Pattern;


@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class TeamDto {
    @NotBlank
    private String id;
    @NotBlank
    private String companyId;
    @NotBlank
    private String name;
    private boolean archived;
    @Timezone(groups = {Group1.class, Group2.class})
    @NotBlank
    private String timezone;
    @NotBlank
    @DayOfWeek
    private String dayWeekStarts;
    @NotBlank
    @Pattern(regexp = "^#([A-Fa-f0-9]{6}|[A-Fa-f0-9]{3})$")
    private String color;
}
