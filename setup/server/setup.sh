#!/bin/bash

# Exit on any error
set -e

# Configuration
SERVER_USER="root"  # Replace with your server username
SERVER_IP="suiteonix.com"  # Replace with your server IP
SERVER_DIR="./suiteonix"  # Replace with your target directory on server

# Step 1: Copy the server folder to the server using rsync (including hidden files)
echo "Copying server folder to the server..."
rsync -avz --progress ./server/ ${SERVER_USER}@${SERVER_IP}:${SERVER_DIR}

# Step 2: SSH into the server and execute the remaining steps
echo "Connecting to the server and executing setup..."
ssh ${SERVER_USER}@${SERVER_IP} << 'EOF'
    # Step 3: Navigate to the copied folder
    cd ./suiteonix

    # Step 4: Update and upgrade the server
    echo "Updating and upgrading the server..."
    apt-get update
    apt-get upgrade -y

    # Step 5: Move .env file to / directory
    echo "Moving .env file to root directory..."
    cp .env /

    # Step 6: Install required software

    echo "Installing Certbot..."
    apt-get install -y python3-certbot python3-certbot-nginx

    echo "Installing Node.js (latest version) and PM2..."
    curl -fsSL https://deb.nodesource.com/setup_current.x | bash -
    apt-get install -y nodejs
    npm install -g pm2

    # Step 7: Add variables in the .env file to ENVIRONMENT
    echo "Adding environment variables to system..."
    set -a
    source /.env
    set +a

    # Step 8: Move nginx configuration files to /etc/nginx/conf.d
    echo "Moving nginx configuration files..."
    mkdir -p /etc/nginx/conf.d
    cp /etc/nginx/sites-available/default /etc/nginx/sites-available/default_old || true
    rm -f /etc/nginx/sites-available/default || true
    cp -f nginx/default.conf /etc/nginx/sites-available/default || true
    cp -f nginx/*.conf /etc/nginx/conf.d/

    # Step 9: create registry password
    mkdir auth
    touch auth/htpasswd
    docker run --entrypoint htpasswd httpd:2 -Bbn $REPOSITORY_USERNAME $REPOSITORY_PASSWORD > auth/htpasswd

    # Step 10: Start Docker Compose
    echo "Starting Docker Compose..."
    docker compose up --build -d

    # Step 11: Test nginx configuration
    echo "Testing nginx configuration..."
    nginx -t

    # Step 12: Restart nginx
    echo "Restarting nginx..."
    systemctl restart nginx

    echo "Setup completed successfully!"
EOF

echo "Deployment completed!"


#run certbot --nginx
#CERTBOT AUTO RENEW
#echo "0 0,12 * * * root /opt/certbot/bin/python -c 'import random; import time; time.sleep(random.random() * 3600)' && sudo certbot renew -q" | sudo tee -a /etc/crontab > /dev/null
