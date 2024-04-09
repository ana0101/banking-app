package persistence;

import model.transfer.Transfer;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class TransferRepository implements GenericRepository<Transfer> {
    private ArrayList<Transfer> transfers = new ArrayList<>();

    @Override
    public int add(Transfer transfer) {
        if (!transfers.contains((transfer))) {
            transfers.add(transfer);
            return transfer.getTransferId();
        }
        return 0;
    }

    @Override
    public Transfer get(int id) {
        return transfers.stream()
                .filter(transfer -> transfer.getTransferId() == id)
                .findFirst()
                .orElse(null);
    }

    public List<Transfer> getTransfersByAccountId (int accountId) {
        return transfers.stream()
                .filter(transfer -> transfer.getPayerAccountId() == accountId ||
                        transfer.getRecipientAccountId() == accountId)
                .collect(Collectors.toList());
    }

    @Override
    public void update(Transfer entity) {

    }

    @Override
    public void delete(Transfer entity) {

    }
}
