set -e

echo "Waiting for MongoDB to be available..."
until mongosh --host mongodb -u "$MONGODB_USERNAME" -p "$MONGODB_PASSWORD" --authenticationDatabase admin --eval "db.adminCommand('ping')" >/dev/null 2>&1; do
  sleep 2
done

echo "MongoDB is up, initializing replica set..."

mongosh --host mongodb -u "$MONGODB_USERNAME" -p "$MONGODB_PASSWORD" --authenticationDatabase admin <<EOF
try {
  rs.initiate({
    _id: "myReplicaSet",
    members: [
      { _id: 0, host: "mongodb:27017", priority: 2 }
    ]
  });
  print("Replica set initialized successfully");
} catch(e) {
  print("Replica set already initialized or failed:", e);
}
EOF

echo "Mongo setup container finished."



