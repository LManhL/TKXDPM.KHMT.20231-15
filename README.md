# TKXDPM.VN.20231-15

## Team member

| Name              | Role        |
| :-----------------| :---------- |
| Nguyễn Văn Mạnh   | Team Leader |
| Trần Văn Mạnh     | Member      |
| Đàm Thị Phương Mai| Member      |
| Nguyễn Đức Minh   | Member      |


## Report Content

<details>
  <summary>From 27/11/2023 ~ to 3/12/2023 </summary>
<br>
<details>
<summary>Team Member 1: Nguyễn Văn Mạnh</summary>
<br>

- Assigned tasks:
  - Find high coulping in all classes related to ordering functionality

- Implementation details:
  - Pull Request(s): https://github.com/LManhL/TKXDPM.KHMT.20231-15/pull/1
  - Specific task details:
    - Read through the entire class related to ordering functionality to find high coupling
  
</details>

<details>
<summary>Team Member 2: Trần Văn Mạnh</summary>
<br>

- Assigned tasks: find coupling in:
  - BaseController, HomeController, 
  - entity/cart/... , entity/media/..., 

- Implementation details:
  - Pull Request(s): https://github.com/LManhL/TKXDPM.KHMT.20231-15/pull/2
  - Specific task details:
    - BaseController and Cart are data coupled because in BaseController.checkMediaInCart(), BaseController and Cart share 'media' data.
</details>

<details>
<summary>Team Member 3: Đàm Thị Phương Mai</summary>
<br>

- Assigned tasks: find coupling in class related to payment

- Implementation details:
  - Pull Request(s): https://github.com/LManhL/TKXDPM.KHMT.20231-15/pull/9
  - Specific implementation details:
    - Read through the entire class related to ordering functionality to find high coupling
</details>

<details>
<summary>Team Member 4: Nguyễn Đức Minh</summary>
<br>

- Assigned tasks:

  - Task 1: Comment coupling code in the checkout subsystem (interbank).

- Implementation details:
  - Pull Request(s): https://github.com/LManhL/TKXDPM.KHMT.20231-15/pull/3
  - Specific implementation details:
    - Read through the entire class related to ordering functionality to find high coupling
</details>
</details>





<details>
  <summary>From 04/12/2023 ~ to 10/12/2023 </summary>
<br>
<details>
<summary>Team Member 1: Nguyễn Văn Mạnh</summary>
<br>

- Assigned tasks:
  - Design UI for admin order management section, including the order list screen and order details screen.
- Implementation details:
  - Pull Request(s): 
    - https://github.com/LManhL/TKXDPM.KHMT.20231-15/pull/5
    - https://github.com/LManhL/TKXDPM.KHMT.20231-15/pull/11
  - Specific task details: The purpose of this pull request is to implement the UI design for the admin order management section. It includes creating screens for both the order list and order details. The changes involve styling, layout, and ensuring a user-friendly interface for efficient order management on the admin side.
  
</details>

<details>
<summary>Team Member 2: Trần Văn Mạnh</summary>
<br>

- Assigned tasks: Update UI have function cancel order
- Implementation details:
  - Pull Request(s): 
      - https://github.com/LManhL/TKXDPM.KHMT.20231-15/pull/6
      - https://github.com/LManhL/TKXDPM.KHMT.20231-15/pull/12
  - Specific task details:
      - Update file cart.fxml have cancel button
</details>

<details>
<summary>Team Member 3: Đàm Thị Phương Mai</summary>
<br>

- Assigned tasks: 
- Implementation details:
  - Pull Request(s):
    - https://github.com/LManhL/TKXDPM.KHMT.20231-15/pull/8
  - Specific task details:
</details>

<details>
<summary>Team Member 4: Nguyễn Đức Minh</summary>
<br>

- Assigned tasks: Create UI and base class for Admin screen
- Implementation details:
  - Pull Request(s):
    - https://github.com/LManhL/TKXDPM.KHMT.20231-15/pull/7
    - https://github.com/LManhL/TKXDPM.KHMT.20231-15/pull/10
  - Specific task details:
      - Find cohesion in the code
      - Create UI and base class for Admin screen
</details>
</details>



<details>
  <summary>From 11/12/2023 ~ to 17/12/2023 </summary>
<br>
<details>
<summary>Team Member 1: Nguyễn Văn Mạnh</summary>
<br>

- Assigned tasks:The Open Closed Principle
  - 
- Implementation details:
  - Pull Request(s): 
    - 
  - Specific task details: 
  
</details>

<details>
<summary>Team Member 2: Trần Văn Mạnh</summary>
<br>

- Assigned tasks:  The Interface Segregation Principle + The Dependency Inversion Principle
- Implementation details:
  - Pull Request(s): 
      - 
  - Specific task details:
      - 1. The Interface Segregation Principle:
        - PaymentController has a InterbankSubsystem object, that implements InterbankInterface. But PaymentController doesn't use 1 of InterbankInterface methods (method 'refund'). In this case, it is better to divide InterbankInterface to 2 interfaces.
      - 2. The Dependency Inversion Principle:
        - Nothing found.
</details>

<details>
<summary>Team Member 3: Đàm Thị Phương Mai</summary>
<br>

- Assigned tasks: The Liskov Substitution Principle
- Implementation details:
  - Pull Request(s):
    - 
  - Specific task details:
</details>

<details>
<summary>Team Member 4: Nguyễn Đức Minh</summary>
<br>

- Assigned tasks: The Single Responsibility Principle
- Implementation details:
  - Pull Request(s):
    - 
  - Specific task details:
      - 
</details>
</details>







---


