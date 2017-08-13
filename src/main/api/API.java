package api;


public interface API {

    void readAllTable(int tableNumber);

    void addOperations(int tableNumber);

    void updateOperations(int tableNumber);

    void deleteOperations(int tableName);

}
