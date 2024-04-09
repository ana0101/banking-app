package persistence;

import model.account.DepositType;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class DepositTypeRepository {
    private ArrayList<DepositType> depositTypes = new ArrayList<>();

    public void init() {
        depositTypes.add(new DepositType(1, 5));
        depositTypes.add(new DepositType(3, 5.5));
        depositTypes.add(new DepositType(6, 6));
        depositTypes.add(new DepositType(12, 6.5));
        depositTypes.add(new DepositType(0, 10));
    }

    public DepositType get(int id) {
        return depositTypes.stream()
                .filter(depositType -> depositType.getDepositTypeId() == id)
                .findFirst()
                .orElse(null);
    }

    public List<DepositType> getAll() {
        return depositTypes.stream()
                .collect(Collectors.toList());
    }
}
