import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'phoneFormat',
  standalone: true,
})
export class PhoneFormatPipe implements PipeTransform {
  transform(value: string | undefined | null): string {
    if (!value) return '';

    // Remove all non-digit characters except the leading +
    let cleaned = value.replace(/[^\d+]/g, '');

    // Pattern for +32 0497 93 57 57
    // This is specifically looking for + (2 digits) (4 digits) (2 digits) (2 digits) (2 digits)
    // Or just format based on what we have.

    // Let's assume + (up to 3 digits code) (rest)
    // Actually, Belgian mobile is +32 4XX XX XX XX (9 digits after +32)
    // The user example +32 0497 93 57 57 has 10 digits after +32 if 0 is included.

    if (cleaned.startsWith('+')) {
      // Find the country code (2 or 3 digits)
      // Since we don't know the exact length of the country code easily without a list,
      // let's try a simple pattern matching for +32 and others.

      // If it starts with +32, format as requested.
      if (cleaned.startsWith('+32')) {
        const countryCode = '+32';
        const rest = cleaned.substring(3);
        return this.formatRest(countryCode, rest);
      } else {
         // Generic format for other country codes
         // Let's assume 2 digit code for now if it's + followed by 10+ digits.
         // Or just + followed by the first 2 digits.
         const countryCode = cleaned.substring(0, 3); // +XX
         const rest = cleaned.substring(3);
         return this.formatRest(countryCode, rest);
      }
    }

    return cleaned;
  }

  private formatRest(countryCode: string, rest: string): string {
    // Expected rest for Belgian: 0497 93 57 57 (10 digits)
    // or 497 93 57 57 (9 digits)

    if (rest.length === 10) {
      // 0497 93 57 57
      return `${countryCode} ${rest.substring(0, 4)} ${rest.substring(4, 6)} ${rest.substring(6, 8)} ${rest.substring(8, 10)}`;
    } else if (rest.length === 9) {
      // 497 93 57 57
      return `${countryCode} ${rest.substring(0, 3)} ${rest.substring(3, 5)} ${rest.substring(5, 7)} ${rest.substring(7, 9)}`;
    }

    // fallback for other lengths
    return `${countryCode} ${rest}`;
  }
}
