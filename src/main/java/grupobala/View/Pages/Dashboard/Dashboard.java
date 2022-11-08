package grupobala.View.Pages.Dashboard;

import grupobala.Controller.Extract.ExtractController;
import grupobala.Controller.Transaction.TransactionController;
import grupobala.Entities.Extract.IExtract.IExtract;
import grupobala.Entities.User.User;
import grupobala.View.Components.AvatarCard.AvatarCardComponent;
import grupobala.View.Components.Card.CardVBoxComponent;
import grupobala.View.Components.ExtractList.ExtractList;
import grupobala.View.Components.GoalList.GoalListComponent;
import grupobala.View.Components.NavigationBar.NavigationBar;
import grupobala.View.Components.OperationButton.OperationButton;
import grupobala.View.Components.OperationButton.OperationButton.IconEnum;
import grupobala.View.Components.OperationPopup.OperationPopup;
import grupobala.View.Components.Popup.PopupComponent;
import grupobala.View.Components.TransactionView.TransactionViewComponent;
import grupobala.View.Pages.Page.Page;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class Dashboard implements Page {

    private StackPane mainPane = new StackPane();
    private OperationPopup incomingPopup = new OperationPopup("Nova entrada");
    private OperationPopup outputPopup = new OperationPopup("Nova saída    ");

    private PopupComponent popupConfirmation = new PopupComponent();
    private PopupComponent errorPopup = new PopupComponent();
    private HBox summaryCard = new HBox();

    private ExtractList extract = new ExtractList();

    private NavigationBar navigationBar = new NavigationBar();

    @Override
    public StackPane getMainPane() {
        VBox mainContainer = new VBox();
        VBox container = new VBox();
        ScrollPane clipContainer = new ScrollPane();
        VBox extractList = getExtractList();

        incomingPopup.setOnConfirm(() -> {
            updateValues();
        });

        outputPopup.setOnConfirm(() -> {
            updateValues();
        });

        extractList.getStyleClass().add("extract-list");
        mainContainer.getStyleClass().add("main-container");
        mainPane.getStyleClass().add("dashboard");
        container.getStyleClass().add("container");
        mainPane
            .getStylesheets()
            .add(
                "file:src/main/resources/grupobala/css/Pages/Dashboard/Dashboard.css"
            );
        mainPane
            .getChildren()
            .addAll(
                mainContainer,
                incomingPopup.getComponent(),
                outputPopup.getComponent(),
                errorPopup.getComponent(),
                popupConfirmation.getComponent()
            );

        setSummaryCard();
        mainContainer
            .getChildren()
            .addAll(navigationBar.getComponent(), clipContainer);
        container
            .getChildren()
            .addAll(
                summaryCard,
                extractList,
                new GoalListComponent().getComponent()
            );
        clipContainer.setContent(container);
        clipContainer.setStyle("-fx-background-color: transparent;");
        clipContainer.setFitToHeight(true);
        clipContainer.setFitToWidth(true);

        return mainPane;
    }

    private void updateValues() {
        extract.reloadExtract();

        summaryCard.getChildren().clear();
        setSummaryCard();
    }

    private void setSummaryCard() {
        VBox leftSummary = getLeftSummaryCard();
        VBox rightSummary = getRightSummaryCard();

        summaryCard.getStyleClass().add("summary-card");
        summaryCard.getChildren().addAll(leftSummary, rightSummary);
    }

    private VBox getLeftSummaryCard() {
        VBox leftSummaryCard = new VBox();
        HBox topSide = new HBox();
        HBox bottomSide = new HBox();
        AvatarCardComponent avatarCard = new AvatarCardComponent();
        VBox balanceBox = getBalanceBox();
        double entry, out;

        try {
            IExtract extract = new ExtractController().getExtract();
            entry = extract.getEntry();
            out = extract.getOutput();
        } catch (Exception e) {
            entry = out = -1;
        }

        CardVBoxComponent bottomCardIn = getLeftSummaryBottomCard(
            "Receita mensal",
            entry,
            "#49AD5A"
        );
        CardVBoxComponent bottomCardOut = getLeftSummaryBottomCard(
            "Despesa mensal",
            out,
            "#C54646"
        );

        leftSummaryCard.getStyleClass().add("left-summary");
        topSide.getStyleClass().add("summary-card-top");
        bottomSide.getStyleClass().add("summary-card-bot");

        topSide.getChildren().addAll(avatarCard.getComponent(), balanceBox);

        bottomSide
            .getChildren()
            .addAll(bottomCardIn.getComponent(), bottomCardOut.getComponent());

        leftSummaryCard.getChildren().addAll(topSide, bottomSide);

        return leftSummaryCard;
    }

    private VBox getBalanceBox() {
        VBox vBox = new VBox();
        Text title = new Text("Saldo geral");
        Text balance = new Text(
            String.format("R$ %.2f", new User().getValue())
        );

        vBox.getStyleClass().add("balance-box");
        title.getStyleClass().add("balance-title");
        balance.getStyleClass().add("balance");

        vBox.getChildren().addAll(title, balance);

        return vBox;
    }

    private CardVBoxComponent getLeftSummaryBottomCard(
        String title,
        double value,
        String color
    ) {
        CardVBoxComponent cardVBoxComponent = new CardVBoxComponent();
        Text titleText = new Text(title);
        Text valueText = new Text(String.format("R$ %.2f", value));

        cardVBoxComponent
            .getComponent()
            .getStyleClass()
            .add("left-summary-bottom-card");
        cardVBoxComponent
            .getComponent()
            .getChildren()
            .addAll(titleText, valueText);

        titleText.getStyleClass().add("left-summary-bottom-title");

        valueText.setStyle(
            String.format(
                "-fx-fill: %s; -fx-font-size: 16px; -fx-font-weight: bold",
                color
            )
        );

        return cardVBoxComponent;
    }

    private VBox getRightSummaryCard() {
        VBox rightSummaryCard = new VBox();

        Text title = new Text("Acesso rápido");
        HBox quickActions = getQuickActions();

        rightSummaryCard.getStyleClass().add("summary-card-right");
        title.getStyleClass().add("quick-access-title");

        rightSummaryCard.getChildren().addAll(title, quickActions);

        return rightSummaryCard;
    }

    private HBox getQuickActions() {
        HBox quickActions = new HBox();
        OperationButton outputButton = new OperationButton();
        OperationButton incomingButton = new OperationButton();
        OperationButton goalButton = new OperationButton();

        quickActions.getStyleClass().add("quick-actions");

        quickActions
            .getChildren()
            .addAll(
                outputButton.getComponent("SAÍDA", IconEnum.SAIDA),
                incomingButton.getComponent("ENTRADA", IconEnum.ENTRADA),
                goalButton.getComponent("META", IconEnum.META)
            );

        incomingButton
            .getComponent()
            .setOnMouseClicked(e -> {
                incomingPopup.getPopup().showPopup();
            });

        outputButton
            .getComponent()
            .setOnMouseClicked(e -> {
                outputPopup.getPopup().showPopup();
            });

        return quickActions;
    }

    private VBox getExtractList() {
        VBox extractContainer = new VBox();
        extractContainer.getStyleClass().add("extract-list");

        extract.setOnMouseClicked(transaction -> {
            TransactionViewComponent transactionView = new TransactionViewComponent(
                transaction
            );
            mainPane.getChildren().add(transactionView.getComponent());
            transactionView.setOnDelete(transactionToDelete -> {
                transactionView.getPopup().hidePopup();
                popupRemoveTransactionConfirmation(
                    transactionToDelete.getId(),
                    transactionToDelete.getValue()
                );
            });
            transactionView.getPopup().showPopup();
        });

        extractContainer.getChildren().add(extract.getComponent());

        return extractContainer;
    }

    private void popupRemoveTransactionError(
        int idTransaction,
        double transactionValue
    ) {
        VBox card = new CardVBoxComponent().getComponent();
        Button closePopup = new Button("X");
        Text text = new Text("Não foi possível remover a movimentação");
        VBox textVBox = new VBox();
        Image alertImg = new Image(
            "file:src/main/resources/grupobala/images/alert.png"
        );
        ImageView alert = new ImageView(alertImg);
        HBox alertHBox = new HBox();

        VBox.setVgrow(textVBox, Priority.ALWAYS);
        card.getChildren().addAll(alertHBox, text, textVBox);
        errorPopup.getComponent().getChildren().add(card);
        textVBox.getChildren().add(text);
        alertHBox.getChildren().addAll(alert, closePopup);

        textVBox.getStyleClass().add("text-box");

        alert.setFitHeight(25);
        alert.setFitWidth(25);
        alert.setPreserveRatio(true);

        alertHBox.getStyleClass().add("alert-box");
        textVBox.getStyleClass().add("text-box");

        card.getStyleClass().add("remove-card");
        closePopup.getStyleClass().add("close-popup-error");
        text.getStyleClass().add("text-error");

        try {
            TransactionController transactionController = new TransactionController();
            transactionController.removeTransaction(
                new User().getID(),
                idTransaction,
                transactionValue,
                new User().getValue()
            );
            updateValues();
        } catch (Exception error) {
            errorPopup.showPopup();

            closePopup.setOnAction(e -> {
                errorPopup.hidePopup();
            });
        }
    }

    private void popupRemoveTransactionConfirmation(
        int idTransaction,
        double transactionValue
    ) {
        VBox card = new CardVBoxComponent().getComponent();
        Button confirmation = new Button("Confirmar");
        Button closePopup = new Button("X");
        Text text = new Text("Deseja apagar esta movimentação?");
        VBox vbox = new VBox();

        vbox.getChildren().add(closePopup);
        card.getChildren().addAll(vbox, text, confirmation);
        popupConfirmation.getComponent().getChildren().addAll(card);

        vbox.getStyleClass().add("confirmation-box");
        card.getStyleClass().add("confirmation-card");
        closePopup.getStyleClass().add("close-popup");
        text.getStyleClass().add("confirmation-text");
        confirmation.getStyleClass().add("confirmation-button");

        popupConfirmation.showPopup();

        confirmation.setOnAction(e -> {
            popupConfirmation.hidePopup();
            popupRemoveTransactionError(idTransaction, transactionValue);
        });

        closePopup.setOnAction(e -> {
            popupConfirmation.hidePopup();
        });
    }
}
