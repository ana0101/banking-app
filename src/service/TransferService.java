package service;

import exception.InvalidDataException;
import model.transfer.Transfer;
import persistence.TransferRepository;

import java.util.ArrayList;
import java.util.List;

public class TransferService {
    public TransferRepository transferRepository = new TransferRepository();

    public int addTransfer(int payerAccountId, int recipientAccountId, double amount, String description) throws InvalidDataException {
        if (payerAccountId == recipientAccountId) {
            throw new InvalidDataException("Payer account can not be the same as the recipient account");
        }
        if (amount <= 0) {
            throw new InvalidDataException("Invalid amount: must be positive");
        }
        Transfer transfer = new Transfer(payerAccountId, recipientAccountId, amount, description);
        return transferRepository.add(transfer);
    }

    public Transfer getTransfer(int transferId) {
        return transferRepository.get(transferId);
    }

    public List<Transfer> getTransfersByAccountId(int accountId) {
        return transferRepository.getTransfersByAccountId(accountId);
    }
}
