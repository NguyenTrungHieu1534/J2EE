package BookManagementMenu;

import java.util.*;
import java.util.stream.Collectors;

public class BT1 {

    private int id;
    private String title;
    private String author;
    private double price;

    public BT1() {
    }

    public BT1(int id, String title, String author, double price) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public double getPrice() {
        return price;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void input(Scanner x) {
        System.out.print("Nhập mã sách: ");
        this.id = Integer.parseInt(x.nextLine());

        System.out.print("Nhập tên sách: ");
        this.title = x.nextLine();

        System.out.print("Nhập tác giả: ");
        this.author = x.nextLine();

        System.out.print("Nhập giá sách: ");
        this.price = Double.parseDouble(x.nextLine());
    }

    public void output() {
        System.out.printf(
                "BOOK | id=%d | title=%s | author=%s | price=%.2f%n",
                id, title, author, price
        );
    }
    public static void main(String[] args) {

        List<BT1> listBook = new ArrayList<>();
        Scanner x = new Scanner(System.in);

        String menu = """
                ===============================
                Book Management Menu
                1. Thêm 1 cuốn sách
                2. Xóa 1 cuốn sách
                3. Thay đổi thông tin sách
                4. Xuất danh sách sách
                5. Tìm sách có tiêu đề "Lập trình"
                6. Lấy N sách giá cao nhất
                7. Tìm sách theo tác giả
                0. Thoát
                ===============================
                Chọn chức năng: 
                """;

        int chon;

        do {
            System.out.print(menu);
            chon = Integer.parseInt(x.nextLine());

            switch (chon) {
                case 1 -> {
                    BT1 b = new BT1();
                    b.input(x);
                    listBook.add(b);
                    System.out.println("Thêm sách thành công!");
                }
                case 2 -> {
                    System.out.print("Nhập mã sách cần xóa: ");
                    int id = Integer.parseInt(x.nextLine());

                    BT1 find = listBook.stream()
                            .filter(b -> b.getId() == id)
                            .findFirst()
                            .orElse(null);

                    if (find != null) {
                        listBook.remove(find);
                        System.out.println("Đã xóa sách!");
                    } else {
                        System.out.println("Không tìm thấy sách!");
                    }
                }
                case 3 -> {
                    System.out.print("Nhập mã sách cần chỉnh sửa: ");
                    int id = Integer.parseInt(x.nextLine());

                    BT1 find = listBook.stream()
                            .filter(b -> b.getId() == id)
                            .findFirst()
                            .orElse(null);

                    if (find != null) {
                        System.out.println("Nhập thông tin mới:");
                        find.input(x);
                        System.out.println("Cập nhật thành công!");
                    } else {
                        System.out.println("Không tìm thấy sách!");
                    }
                }
                case 4 -> {
                    System.out.println("DANH SÁCH SÁCH:");
                    if (listBook.isEmpty()) {
                        System.out.println("(Danh sách rỗng)");
                    } else {
                        listBook.forEach(BT1::output);
                    }
                }
                case 5 -> {
                    listBook.stream()
                            .filter(b -> b.getTitle().toLowerCase().contains("lập trình"))
                            .forEach(BT1::output);
                }
                case 6 -> {
                    System.out.print("Nhập số lượng sách cần lấy: ");
                    int n = Integer.parseInt(x.nextLine());

                    listBook.stream()
                            .sorted((b1, b2) -> Double.compare(b2.getPrice(), b1.getPrice()))
                            .limit(n)
                            .forEach(BT1::output);
                }
                case 7 -> {
                    System.out.print("Nhập tên tác giả: ");
                    String input = x.nextLine();

                    Set<String> authorSet = Arrays.stream(input.split(","))
                            .map(String::trim)
                            .map(String::toLowerCase)
                            .collect(Collectors.toSet());

                    listBook.stream()
                            .filter(b -> authorSet.contains(b.getAuthor().toLowerCase()))
                            .forEach(BT1::output);
                }

                case 0 -> System.out.println("Thoát chương trình!");

                default -> System.out.println("Lựa chọn không hợp lệ!");
            }

        } while (chon != 0);
    }
}
