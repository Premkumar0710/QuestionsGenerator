This Repository contains all the implementations that are required for Generating Practice questions like SQL, DSA, System design straight to your whatsapp.

Overview
The Daily Tech Question Sender is a backend system built with Java and Spring Boot that delivers daily technical questions to subscribed users via WhatsApp, using the Twilio API. Users can choose from multiple learning tracks such as:

• SQL
• Data Structures & Algorithms (DSA)
• Java & Spring Boot
• DevOps
• Low-Level Design (LLD)
• High-Level Design (HLD)

The application is designed for automation, scalability, and a smooth learning experience. It leverages MySQL for storage, GitHub Actions for CI/CD, and a modular architecture for future cloud deployment.

Features
• Users can subscribe to any learning track.
• Automatically sends daily questions to WhatsApp using Twilio.
• Each track has its own question table for efficient storage and retrieval.
• Scheduling logic ensures delivery at a fixed time (e.g., 7 AM).
• GitHub Actions integrated for CI/CD pipeline automation.
• Designed for 24x7 server operation (planned EC2 deployment).

How it works ?
• Each user is mapped to a track.
• A scheduled task fetches 1 or 2 questions per day from that track's table.
• Messages are formatted and sent via Twilio WhatsApp API.
• Logic skips already sent questions and tracks progress internally.
GitHub Actions - CI/CD
• Trigger: On push to main / feature branch.
Jobs:
o Build and test Spring Boot app using Maven.
o Optional deployment step (planned for AWS EC2 via SSH or Docker).
