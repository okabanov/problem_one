package com.okabanov.dto;

public class TransferDto {
    private int transferredAmount;
    private String transferTo;

    public TransferDto(int transferredAmount, String transferTo) {
        this.transferredAmount = transferredAmount;
        this.transferTo = transferTo;
    }

    public int getTransferredAmount() {
        return transferredAmount;
    }

    public void setTransferredAmount(int transferredAmount) {
        this.transferredAmount = transferredAmount;
    }

    public String getTransferTo() {
        return transferTo;
    }

    public void setTransferTo(String transferTo) {
        this.transferTo = transferTo;
    }
}
