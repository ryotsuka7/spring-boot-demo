package example.model;

public class Item {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column item.id
     *
     * @mbg.generated Sat Nov 05 17:42:46 JST 2022
     */
    private Integer id;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column item.item_name
     *
     * @mbg.generated Sat Nov 05 17:42:46 JST 2022
     */
    private String itemName;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column item.id
     *
     * @return the value of item.id
     *
     * @mbg.generated Sat Nov 05 17:42:46 JST 2022
     */
    public Integer getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column item.id
     *
     * @param id the value for item.id
     *
     * @mbg.generated Sat Nov 05 17:42:46 JST 2022
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column item.item_name
     *
     * @return the value of item.item_name
     *
     * @mbg.generated Sat Nov 05 17:42:46 JST 2022
     */
    public String getItemName() {
        return itemName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column item.item_name
     *
     * @param itemName the value for item.item_name
     *
     * @mbg.generated Sat Nov 05 17:42:46 JST 2022
     */
    public void setItemName(String itemName) {
        this.itemName = itemName;
    }
}