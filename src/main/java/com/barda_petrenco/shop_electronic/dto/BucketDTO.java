package com.barda_petrenco.shop_electronic.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BucketDTO {
    private int amountProducts;
    private Double sum;
    private List<BucketDetailDTO> bucketDetails;

    // Если вы используете Builder, то рекомендуется инициализировать коллекции в нем
    public static class BucketDTOBuilder {
        private List<BucketDetailDTO> bucketDetails = new ArrayList<>();
    }

    public void aggregate() {
        this.amountProducts = bucketDetails.size();
        this.sum = bucketDetails.stream()
                .map(BucketDetailDTO::getSum)
                .mapToDouble(Double::doubleValue)
                .sum();
    }
}
