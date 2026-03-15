# Stage 1: Build the Angular application
FROM node:22-alpine AS builder

WORKDIR /app

# Copy package.json and yarn.lock
COPY package.json yarn.lock ./

# Install dependencies
RUN yarn install --frozen-lockfile

# Copy the rest of the application code
COPY . .

# Build the application
RUN yarn build --configuration production

# Stage 2: Serve the application with Nginx
FROM nginx:alpine

# Copy the built application from the builder stage
COPY --from=builder /app/dist/addresstool/browser /usr/share/nginx/html

# Copy the custom Nginx configuration
COPY nginx.conf /etc/nginx/nginx.conf

# Expose port 80
EXPOSE 80

# Start Nginx
CMD ["nginx", "-g", "daemon off;"]
