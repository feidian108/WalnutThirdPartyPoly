package com.walnut.cloud.open.common.util.http;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.InputStream;
import java.io.Serializable;


@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class InputStreamData implements Serializable {
  private static final long serialVersionUID = -4627006604779378520L;
  private InputStream inputStream;
  private String filename;
}
