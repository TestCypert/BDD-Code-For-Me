package com.boi.grp.request;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

/**
 * Created by C112083 on 10/02/2021.
 */
@Data
@Builder
@ToString
public class Root {

    private Request request;

}
