package test.tampilan;

import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import test.controller.PengendaliForum;
import test.model.ForumDiskusi;
import test.model.Pengguna;
import test.helper.session;
import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.geometry.Pos;

public class ForumWarga {
    private final PengendaliForum pengendaliForum;
    private ObservableList<ForumDiskusi> daftarPost;
    private ListView<ForumDiskusi> listView;
    private ScrollPane detailPane;

    public ForumWarga(PengendaliForum pengendaliForum) {
        this.pengendaliForum = pengendaliForum;
        this.daftarPost = pengendaliForum.getSemuaPost(); // Inisialisasi daftarPost
    }

    public ForumWarga(PengendaliForum pengendaliForum2, Object object) {
        this.pengendaliForum = new PengendaliForum();
        this.daftarPost = pengendaliForum.getSemuaPost(); // Inisialisasi daftarPost
    }

    public Parent getView() {
        BorderPane layout = new BorderPane();
        layout.setPadding(new Insets(10));
        layout.setStyle("-fx-background-color: #f5f5f5;");

        // Header dengan gaya modern
        HBox headerBox = new HBox();
        headerBox.setPadding(new Insets(15));
        headerBox.setStyle("-fx-background-color: #2E7D32; -fx-background-radius: 10 10 0 0;");
        
        Label title = new Label("🌿 Forum Komunitas");
        title.setFont(Font.font("Arial", FontWeight.BOLD, 24));
        title.setStyle("-fx-text-fill: white;");
        
        headerBox.getChildren().add(title);
        layout.setTop(headerBox);

        TabPane tabPane = new TabPane();
        Tab tabForum = new Tab("Forum Diskusi");
        Tab tabBuat = new Tab("Buat Post Baru");

        tabForum.setClosable(false);
        tabBuat.setClosable(false);

        // ==== Tab Forum ====
        VBox forumTab = new VBox(10);
        forumTab.setPadding(new Insets(15));
        forumTab.setStyle("-fx-background-color: #ffffff; -fx-background-radius: 0 0 10 10;");

        listView = new ListView<>();
        listView.setItems(daftarPost); // Gunakan daftarPost yang sudah diinisialisasi
        listView.setPlaceholder(new Label("Belum ada post di forum. Jadilah yang pertama berbagi!"));
        listView.setCellFactory(param -> new PostListCell());
        listView.setStyle("-fx-background-color: transparent; -fx-control-inner-background: transparent;");

        detailPane = new ScrollPane();
        detailPane.setFitToWidth(true);
        detailPane.setStyle("-fx-background: #ffffff; -fx-border-color: #e0e0e0;");
        detailPane.setVisible(false);

        listView.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, post) -> {
            if (post != null) {
                detailPane.setContent(createPostDetail(post));
                detailPane.setVisible(true);
            } else {
                detailPane.setVisible(false);
            }
        });

        // SplitPane untuk tampilan split antara list dan detail
        SplitPane splitPane = new SplitPane();
        splitPane.getItems().addAll(listView, detailPane);
        splitPane.setDividerPositions(0.4);
        
        forumTab.getChildren().add(splitPane);
        tabForum.setContent(forumTab);

        // ==== Tab Buat Post ====
        VBox buatTab = new VBox(15);
        buatTab.setPadding(new Insets(20));
        buatTab.setStyle("-fx-background-color: #ffffff; -fx-background-radius: 0 0 10 10;");

        Label buatPostLabel = new Label("Buat Post Baru");
        buatPostLabel.setFont(Font.font("Arial", FontWeight.BOLD, 18));
        buatPostLabel.setStyle("-fx-text-fill: #2E7D32;");

        TextField judulInput = new TextField();
        TextArea isiInput = new TextArea();
        Button kirimBtn = new Button("Posting");
        
        // Styling untuk input
        judulInput.setPromptText("Judul postingan...");
        judulInput.setStyle("-fx-background-color: #f5f5f5; -fx-background-radius: 5; -fx-padding: 10;");
        
        isiInput.setPromptText("Apa yang ingin Anda bagikan?");
        isiInput.setWrapText(true);
        isiInput.setStyle("-fx-background-color: #f5f5f5; -fx-background-radius: 5; -fx-padding: 10;");
        
        kirimBtn.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-font-weight: bold; -fx-background-radius: 5; -fx-padding: 10 20;");
        kirimBtn.setMaxWidth(Double.MAX_VALUE);

        kirimBtn.setOnAction(e -> {
            String judul = judulInput.getText().trim();
            String isi = isiInput.getText().trim();

            if (judul.isEmpty() || isi.isEmpty()) {
                NotifikasiJadwal.tampilkanPeringatan("Kosong", "Judul dan isi tidak boleh kosong.");
                return;
            }

            // Tambahkan post baru
            pengendaliForum.tambahPost(judul, isi, session.penggunaAktif);
            
            // Refresh listView
            listView.refresh();
            // Optionally, scroll to the last post and select it
            if (!daftarPost.isEmpty()) {
                listView.scrollTo(daftarPost.size() - 1);
                listView.getSelectionModel().select(daftarPost.size() - 1);
            }

            judulInput.clear();
            isiInput.clear();
            
            // Pindah ke tab forum setelah posting
            tabPane.getSelectionModel().select(tabForum);
        });

        buatTab.getChildren().addAll(buatPostLabel, judulInput, isiInput, kirimBtn);
        tabBuat.setContent(buatTab);

        tabPane.getTabs().addAll(tabForum, tabBuat);
        layout.setCenter(tabPane);

        // ==== Tombol Kembali ====
        Button kembaliBtn = new Button("← Kembali ke Beranda");
        kembaliBtn.setStyle("-fx-background-color: transparent; -fx-text-fill: #2E7D32; -fx-border-color: #2E7D32; -fx-border-radius: 5;");
        kembaliBtn.setOnAction(e -> {
            HalamanUtama halaman = new HalamanUtama();
            halaman.start(HalamanUtama.primaryStageGlobal);
        });
        
        HBox bottomBox = new HBox(kembaliBtn);
        bottomBox.setPadding(new Insets(15));
        bottomBox.setStyle("-fx-background-color: #ffffff; -fx-background-radius: 0 0 10 10;");
        layout.setBottom(bottomBox);

        return layout;
    }

    /**
     * Custom cell untuk menampilkan post di listview
     */
    private class PostListCell extends ListCell<ForumDiskusi> {
        private final HBox content;
        private final Text judul;
        private final Text penulis;
        private final Text waktu;
        private final Circle avatarPlaceholder;
        
        public PostListCell() {
            super();
            
            // Avatar placeholder (tanpa gambar)
            avatarPlaceholder = new Circle(20);
            avatarPlaceholder.setFill(Color.LIGHTGRAY);
            
            VBox vBox = new VBox(5);
            judul = new Text();
            judul.setFont(Font.font("Arial", FontWeight.BOLD, 14));
            
            HBox infoBox = new HBox(10);
            penulis = new Text();
            penulis.setFont(Font.font("Arial", 12));
            penulis.setFill(Color.GRAY);
            
            waktu = new Text();
            waktu.setFont(Font.font("Arial", 10));
            waktu.setFill(Color.LIGHTGRAY);
            
            infoBox.getChildren().addAll(penulis, waktu);
            vBox.getChildren().addAll(judul, infoBox);
            
            content = new HBox(10);
            content.setAlignment(Pos.CENTER_LEFT);
            content.getChildren().addAll(avatarPlaceholder, vBox);
            content.setPadding(new Insets(10));
        }
        
        @Override
        protected void updateItem(ForumDiskusi post, boolean empty) {
            super.updateItem(post, empty);
            
            if (post != null && !empty) {
                judul.setText(post.getJudul());
                penulis.setText("@" + (post.getPenulis() != null ? post.getPenulis().getUsername() : "Anonim"));
                
                // Format waktu posting
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMM yyyy HH:mm");
                waktu.setText("• " + LocalDateTime.now().format(formatter));
                
                setGraphic(content);
                setStyle("-fx-background-color: white; -fx-background-radius: 5; -fx-padding: 5;");
            } else {
                setGraphic(null);
                setText(null);
            }
        }
    }

    /**
     * Membuat tampilan detail post yang lebih menarik
     */
    private VBox createPostDetail(ForumDiskusi post) {
        VBox postDetail = new VBox(15);
        postDetail.setPadding(new Insets(20));
        postDetail.setStyle("-fx-background-color: white;");
        
        // Header post (penulis dan waktu)
        HBox headerBox = new HBox(10);
        headerBox.setAlignment(Pos.CENTER_LEFT);
        
        // Gunakan Circle sebagai placeholder avatar
        Circle avatar = new Circle(25);
        avatar.setFill(Color.LIGHTGRAY);
        
        VBox userBox = new VBox(2);
        Label usernameLabel = new Label("@" + (post.getPenulis() != null ? post.getPenulis().getUsername() : "Anonim"));
        usernameLabel.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMMM yyyy 'pukul' HH:mm");
        Label timeLabel = new Label(LocalDateTime.now().format(formatter));
        timeLabel.setFont(Font.font("Arial", 10));
        timeLabel.setTextFill(Color.GRAY);
        
        userBox.getChildren().addAll(usernameLabel, timeLabel);
        headerBox.getChildren().addAll(avatar, userBox);
        
        // Konten post
        Label judulLabel = new Label(post.getJudul());
        judulLabel.setFont(Font.font("Arial", FontWeight.BOLD, 18));
        judulLabel.setWrapText(true);
        
        TextFlow isiFlow = new TextFlow();
        Text isiText = new Text(post.getIsiPost());
        isiText.setFont(Font.font("Arial", 14));
        isiFlow.getChildren().add(isiText);
        isiFlow.setMaxWidth(400);
        
        // Action buttons
        HBox actionBox = new HBox(15);
        Button likeBtn = new Button("👍 Suka");
        Button commentBtn = new Button("💬 Komentar");
        Button shareBtn = new Button("🔗 Bagikan");
        
        likeBtn.setStyle("-fx-background-color: transparent; -fx-text-fill: #2E7D32;");
        commentBtn.setStyle("-fx-background-color: transparent; -fx-text-fill: #2E7D32;");
        shareBtn.setStyle("-fx-background-color: transparent; -fx-text-fill: #2E7D32;");
        
        actionBox.getChildren().addAll(likeBtn, commentBtn, shareBtn);
        
        postDetail.getChildren().addAll(headerBox, new Separator(), judulLabel, isiFlow, actionBox);
        
        return postDetail;
    }
}