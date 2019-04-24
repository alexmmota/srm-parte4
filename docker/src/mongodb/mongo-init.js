db.createUser(
    {
        user: "srm",
        pwd: "1234",
        roles: [
            {
                role: "readWrite",
                db: "db_srm"
            }
        ]
    }
);