package com.automation.bigbasket.data;

/** One row of testdata.json. Public fields keep Jackson mapping trivial. */
public class TestData {
    public String scenario;
    public String location;
    public String primaryProduct;
    public String brandFilter;
    public String secondaryProduct;
    public int targetQuantity;
    public String expectedLoginErrorKeyword;

    @Override
    public String toString() {
        return scenario;
    }
}
