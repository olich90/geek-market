package ru.gb.springbootdemoapp.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ProductShortDto {

  @NotNull
  @NotEmpty
  private String title;

  @NotNull
  private Float price;

  @NotNull
  private String category;
}
