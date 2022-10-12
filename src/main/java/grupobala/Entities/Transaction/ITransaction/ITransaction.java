package grupobala.Entities.Transaction.ITransaction;

import grupobala.Entities.Category.CategoryEnum;
import java.util.Date;

public interface ITransaction {
    public int getId();

    public double getValor();

    public String getTitle();

    public CategoryEnum getCategory();

    public Date getDate();
}
