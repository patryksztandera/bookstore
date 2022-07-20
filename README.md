###Request body for POST:

/books:

````
{
    "isbn": "1-84356-028-4",
    "title": "Great adventure",
    "price": 10.9,
    "quantity": 54,
    "description": "Short description",
    "publicationDate": "2022",
    "author": "John Smith",
    "publisher": "Secker & Warburg",
    "genre": "Dystopian"
}

````

/orders:

````
{
    "bookIDs" : [
        {
            "ID": 1
        }
    ],
    "clientEmail": "name@mail.com"
}
````
/clients:

````
{
    "name": "John",
    "surname": "Smith",
    "email": "jsmith@mail.com",
    "address" : "10 Park Avenue, New York, USA",
    "password": "StrongPassword051",
    "phone": "123456789"
}
````


