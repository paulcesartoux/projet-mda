| Étape | Tâche | Détail |
| --- | --- | --- |
| 1️⃣ | **Choisir un cas d’étude** | E-commerce, santé, IoT… |
| 2️⃣ | **Créer un CIM** | Use cases, exigences métier |
| 3️⃣ | **Créer un PIM** | Modèle UML (classes, activités…) |
| 4️⃣ | **Créer 2 PSMs** | Exemple : un en Java, un en Web |
| 5️⃣ | **Générer du code** | Automatiser avec EMF, MagicDraw, ou autre |
| 6️⃣ | **Implémenter un prototype** | App fonctionnelle simple |
| 7️⃣ | **Documenter le tout** | Rapport sur chaque étape, transformations, outils utilisés |

```mermaid
flowchart LR
  %% Acteurs
  customer[/"Customer"/]
  admin[/"Administrator"/]

  %% Cas d’usage
  browse((Browse & Search Products))
  cart((Manage Shopping Cart))
  pay((Checkout & Payment))
  track((Track Order Status))
  profile((Manage Profile))
  prod((Manage Products))
  orders((Process Orders))

  %% Relations
  customer --> browse
  customer --> cart
  customer --> pay
  customer --> track
  customer --> profile

  admin --> prod
  admin --> orders

  %% (Optionnel) style acteur
  classDef actorStyle fill:#f5f5f5,stroke:#333,stroke-width:2,stroke-dasharray: 5 2;
  class customer,admin actorStyle;

```

- BPMN Shopmax

### lasses principales à modéliser :

- `User` (`Customer` et `Admin` peuvent en hériter)
    - `id`, `name`, `email`
- `Product`
    - `id`, `name`, `description`, `price`, `stockQuantity`
- `Order`
    - `orderId`, `date`
    - Relations :
        - `User 1 -- * Order`
        - `Order 1 -- * Product`
- `ShoppingCart` (optionnel, mais logique)
- `Payment`

```mermaid
classDiagram
  %% Associations
  User <|-- Customer
  User <|-- Admin
  Customer "1" -- "*" ShoppingCart
  ShoppingCart "*" -- "*" Product
  Customer "1" -- "*" Order
  Order "*" -- "*" Product
  Order "1" -- "1" Payment

  %% Classes
  class User {
    +id : int
    +firstName : string
    +lastName : string
    +email : string
  }

  class Customer {
    +address : string
  }

  class Product {
    +id : int
    +name : string
    +description : string
    +price : float
    +stockQuantity : int
  }

  class ShoppingCart {
    +id : int
    +createdAt : date
  }

  class Order {
    +orderId : int
    +date : date
    +status : string
  }

  class Payment {
    +paymentId : int
    +amount : float
    +method : string
    +status : string
  }

```